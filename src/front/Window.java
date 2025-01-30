package front;

import javax.swing.*;
import java.awt.*;
import db_classes.AppUser;

public class Window {
    private JFrame frame;
    private JButton logoutButton;
    private JButton viewUsersButton;
    private JButton inventoryButton;
    private AppUser currentUser;

    public Window(AppUser user) {
        currentUser = user;
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
        new OtherUsersWindow(currentUser);
        frame.dispose();
    }

    private void openInventoryWindow() {
        JOptionPane.showMessageDialog(frame, "Opening 'Inventory' window...");
    }

    private void logout() {
        frame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        AppUser user = new AppUser("test@example.com", true, 1, "John Doe");
        new Window(user);
    }
}
