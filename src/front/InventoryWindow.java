package front;

import javax.swing.*;
import db_classes.AppUser;
import db_classes.DBUser;
import db_classes.DBStore;
import db_classes.DBInventory;
import db_classes.Connector;
import java.awt.*;
import java.util.Dictionary;
import java.util.List;

public class InventoryWindow {
    private JFrame frame;
    private JButton logoutButton;
    private JButton viewUsersButton;
    private JButton inventoryButton;
    private AppUser currentUser;
    private DBStore dbStore;

    public InventoryWindow(AppUser user) {
        currentUser = user;
        frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(250, 235, 215));

        dbStore = new DBStore(new Connector("jdbc:mysql://localhost:8889", "root", "root"));

        JLabel welcomeLabel = new JLabel("Welcome, " + user.get_name() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(139, 90, 43));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(new Color(250, 235, 215));

        viewUsersButton = new JButton("View Other Users' Information");
        viewUsersButton.setBackground(Color.WHITE);
        viewUsersButton.setForeground(new Color(139, 90, 43));
        viewUsersButton.addActionListener(e -> openOtherUsersWindow());
        buttonPanel.add(viewUsersButton);

        inventoryButton = new JButton("Inventory");
        inventoryButton.setBackground(Color.WHITE);
        inventoryButton.setForeground(new Color(139, 90, 43));
        inventoryButton.addActionListener(e -> openInventoryWindow());
        buttonPanel.add(inventoryButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(new Color(139, 90, 43));
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(logoutButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void openOtherUsersWindow() {
        new OtherUsersWindow(currentUser);
        frame.dispose();
    }

    private void openInventoryWindow() {
        // Get store ID using DBStore
        int storeId = getStoreIdForUser(currentUser.get_id());

        if (storeId == -1) {
            JOptionPane.showMessageDialog(frame, "No store found for this user.");
            return;
        }

        DBInventory dbInventory = new DBInventory(new Connector("jdbc:mysql://localhost:8889", "root", "root"));
        List<String> items = dbInventory.get_items(currentUser.get_id(), storeId);

        // Create the table to display items
        String[] columnNames = {"ID", "Name", "Price"};
        Object[][] data = new Object[items.size()][3];

        for (int i = 0; i < items.size(); i++) {
            // Assuming the item list contains name, price, and id in some format.
            String[] itemDetails = items.get(i).split(","); // Example: "id,name,price"
            data[i][0] = itemDetails[0]; // ID
            data[i][1] = itemDetails[1]; // Name
            data[i][2] = itemDetails[2]; // Price
        }

        JTable inventoryTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        // Create a button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton updateStockButton = new JButton("Update Stock");
        updateStockButton.addActionListener(e -> {
            // Admins should be the only ones who can change stock
            if (currentUser.is_admin()) {
                openStockChangeDialog(inventoryTable);
            } else {
                JOptionPane.showMessageDialog(frame, "Only admins can change stock.");
            }
        });
        buttonPanel.add(updateStockButton);

        // Display inventory window
        JFrame inventoryFrame = new JFrame("Inventory Management");
        inventoryFrame.setSize(600, 400);
        inventoryFrame.setLayout(new BorderLayout());
        inventoryFrame.add(scrollPane, BorderLayout.CENTER);
        inventoryFrame.add(buttonPanel, BorderLayout.SOUTH);
        inventoryFrame.setVisible(true);
    }

    // Method to get store ID for the user from DBStore
    private int getStoreIdForUser(int userId) {
        // Assume the user is assigned to a store and get the store_id from DBStore
        List<String> storeIds = dbStore.get_employees(true, userId, -1); // -1 to fetch all stores for the user
        if (storeIds.isEmpty()) {
            return -1; // No store found for the user
        }
        return Integer.parseInt(storeIds.get(0)); // Return the first store ID found
    }

    // Separate method for admin to confirm stock changes
    private void openStockChangeDialog(JTable inventoryTable) {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to update.");
            return;
        }

        String itemId = (String) inventoryTable.getValueAt(selectedRow, 0);
        String itemName = (String) inventoryTable.getValueAt(selectedRow, 1);

        String[] options = {"Increase", "Decrease"};
        int action = JOptionPane.showOptionDialog(frame, "Would you like to increase or decrease the stock for " + itemName + "?",
                "Update Stock", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (action == JOptionPane.CLOSED_OPTION) return;

        String quantityInput = JOptionPane.showInputDialog(frame, "Enter quantity to " + (action == 0 ? "increase" : "decrease") + ":");
        try {
            int quantity = Integer.parseInt(quantityInput);
            if (quantity < 1) {
                JOptionPane.showMessageDialog(frame, "Quantity must be positive.");
                return;
            }

            // Confirm the action before making changes
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to " + (action == 0 ? "increase" : "decrease") + " the stock by " + quantity + " units?",
                    "Confirm Stock Change", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                DBInventory dbInventory = new DBInventory(new Connector("jdbc:mysql://localhost:8889", "root", "root"));
                dbInventory.change_stock(currentUser.get_id(), getStoreIdForUser(currentUser.get_id()), Integer.parseInt(itemId), (action == 0 ? quantity : -quantity));
                JOptionPane.showMessageDialog(frame, "Stock updated successfully.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid quantity input.");
        }
    }

    private void logout() {
        frame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        String db_url = "jdbc:mysql://localhost:8889";
        String db_id = "root";
        String db_pwd = "root";

        String login = "test@example.com";
        String password = "motdepasse1234";

        Connector connector = new Connector(db_url, db_id, db_pwd);
        DBUser db_user = new DBUser(connector);
        Dictionary<String, String> user_infos = db_user.login(login, password);

        // Create AppUser from user_infos
        AppUser user = new AppUser(user_infos);
        new InventoryWindow(user);
    }
}
