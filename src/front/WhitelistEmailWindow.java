package front;

import javax.swing.*;
import db_classes.AppUser;
import db_classes.DBUser;
import java.awt.*;

public class WhitelistEmailWindow {
    private JFrame frame;
    private JTextField emailField;
    private JButton whitelistButton;
    private AppUser currentUser;
    private DBUser dbUser;
    private static final String STORE_NAME = "DefaultStore";

    public WhitelistEmailWindow(AppUser user, DBUser dbUser) {
        this.currentUser = user;
        this.dbUser = dbUser;

        frame = new JFrame("Whitelist Email");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(250, 235, 215));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBackground(new Color(250, 235, 215));

        emailField = new JTextField();
        panel.add(new JLabel("Enter email to whitelist:"));
        panel.add(emailField);

        whitelistButton = new JButton("Whitelist Email");
        whitelistButton.setBackground(Color.WHITE);
        whitelistButton.setForeground(new Color(139, 90, 43));
        whitelistButton.addActionListener(e -> whitelistEmail());

        frame.add(panel, BorderLayout.CENTER);
        frame.add(whitelistButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void whitelistEmail() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Email cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(frame, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isAdmin = email.endsWith("@test.com");
        if (email.endsWith("@storename.com")) {
            email = email.replace("@storename.com", "@" + STORE_NAME.toLowerCase() + ".com");
        }

        dbUser.whitelist_email(currentUser.get_is_admin(), email);
        JOptionPane.showMessageDialog(frame, "Email whitelisted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
    }
}
