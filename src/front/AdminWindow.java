package front;

import javax.swing.*;

import db_classes.*;

import java.awt.*;

public class AdminWindow {
    private JFrame frame;
    private JButton manageUsersButton;
    private JButton whitelistEmailButton;
    private JButton createStoreButton;
    private JButton manageInventoryButton;
    private JButton logoutButton;
    private AppUser currentUser;
    private DBUser db_user;
    private DBItem db_item;
    private DBStore db_store;
    private DBInventory db_inventory;

    public AdminWindow(AppUser user, DBUser db_u, DBItem db_it, DBStore db_s, DBInventory db_in) {
        DBUser db_user = db_u;
        DBItem db_item = db_it;
        DBStore db_store = db_s;
        DBInventory db_inventory = db_in;
        currentUser = user;
        frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(250, 235, 215));

        JLabel welcomeLabel = new JLabel("Admin Panel - Welcome, " + user.get_name() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(139, 90, 43));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBackground(new Color(250, 235, 215));

        manageUsersButton = new JButton("Manage Users");
        manageUsersButton.setBackground(Color.WHITE);
        manageUsersButton.setForeground(new Color(139, 90, 43));
        manageUsersButton.addActionListener(e -> openManageUsersWindow());
        buttonPanel.add(manageUsersButton);

        whitelistEmailButton = new JButton("Whitelist Email");
        whitelistEmailButton.setBackground(Color.WHITE);
        whitelistEmailButton.setForeground(new Color(139, 90, 43));
        whitelistEmailButton.addActionListener(e -> openWhitelistEmailWindow());
        buttonPanel.add(whitelistEmailButton);

        createStoreButton = new JButton("Create Store");
        createStoreButton.setBackground(Color.WHITE);
        createStoreButton.setForeground(new Color(139, 90, 43));
        createStoreButton.addActionListener(e -> openCreateStoreWindow());
        buttonPanel.add(createStoreButton);

        manageInventoryButton = new JButton("Manage Inventory");
        manageInventoryButton.setBackground(Color.WHITE);
        manageInventoryButton.setForeground(new Color(139, 90, 43));
        manageInventoryButton.addActionListener(e -> openManageInventoryWindow());
        buttonPanel.add(manageInventoryButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(new Color(139, 90, 43));
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(logoutButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void openManageUsersWindow() {
        new ManageUsersWindow(currentUser, db_user, db_item, db_store, db_inventory);
        frame.dispose();
    }

    private void openWhitelistEmailWindow() {
        new WhitelistEmailWindow(currentUser, db_user, db_item, db_store, db_inventory);
        frame.dispose();
    }

    private void openCreateStoreWindow() {
        //new CreateStoreWindow(currentUser);
        frame.dispose();
    }

    private void openManageInventoryWindow() {
        //new ManageInventoryWindow(currentUser);
        frame.dispose();
    }

    private void logout() {
        frame.dispose();
        System.exit(0);
    }

    public static void main(AppUser user, DBUser db_u, DBItem db_it, DBStore db_s, DBInventory db_in) {
        new AdminWindow(user, db_u, db_it, db_s, db_in);
    }
}
