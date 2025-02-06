package front;

import javax.swing.*;

import db_classes.*;

import java.awt.*;
import java.util.Dictionary;


public class Window {
    private JFrame frame;
    private JButton logoutButton;
    private JButton viewUsersButton;
    private JButton inventoryButton;
    private AppUser currentUser;
    private DBUser db_user;
    private DBItem db_item;
    private DBStore db_store;
    private DBInventory db_inventory;

    public Window(AppUser user, DBUser db_u, DBItem db_it, DBStore db_s, DBInventory db_in) {
        currentUser = user;
        DBUser db_user = db_u;
        DBItem db_item = db_it;
        DBStore db_store = db_s;
        DBInventory db_inventory = db_in;
        frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(250, 235, 215));

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
        new OtherUsersWindow(currentUser, db_user, db_item, db_store, db_inventory);
        frame.dispose();
    }

    private void openInventoryWindow() {
        new InventoryWindow(currentUser, db_user, db_item, db_store, db_inventory);
        frame.dispose();
    }

    private void logout() {
        frame.dispose();
        System.exit(0);
    }

    public static void main(AppUser user, DBUser db_u, DBItem db_it, DBStore db_s, DBInventory db_in) {
        new Window(user, db_u, db_it, db_s, db_in);
    }
}
