package front;

import db_classes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;

public class LoginWindow {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setSize(350, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(247, 231, 206));

        placeComponents(panel, frame);
        frame.add(panel);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static void placeComponents(JPanel panel, JFrame frame) {
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(20, 20, 80, 25);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(new Color(166, 123, 91));
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(120, 20, 180, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 60, 80, 25);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(166, 123, 91));
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(120, 60, 180, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 100, 120, 30);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(166, 123, 91));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(130, 90, 60), 2));

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(195, 224, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(166, 123, 91));
            }
        });

        panel.add(loginButton);

        JButton signinButton = new JButton("Signin");
        signinButton.setBounds(180, 100, 120, 30);
        signinButton.setFont(new Font("Arial", Font.BOLD, 14));
        signinButton.setBackground(new Color(166, 123, 91));
        signinButton.setForeground(Color.WHITE);
        signinButton.setFocusPainted(false);
        signinButton.setBorder(BorderFactory.createLineBorder(new Color(130, 90, 60), 2));

        signinButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signinButton.setBackground(new Color(195, 224, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                signinButton.setBackground(new Color(166, 123, 91));
            }
        });

        signinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                SigninWindow.main(new String[]{});
            }
        });

        panel.add(signinButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = userText.getText();
                String password = new String(passwordText.getPassword());

                String db_url = "jdbc:mysql://localhost:8889";
                String db_id = "root";
                String db_pwd = "admin";
                Connector connector = new Connector(db_url, db_id, db_pwd);
                DBUser db_user = new DBUser(connector);
                DBItem db_item = new DBItem(connector);
                DBStore db_store = new DBStore(connector);
                DBInventory db_inventory = new DBInventory(connector);
                Dictionary<String, String> user_infos = db_user.login(email, password);
                AppUser user = new AppUser(user_infos);

                if (!user_infos.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Login Successful!");
                    frame.dispose();
                    if ("admin".equals(user_infos.get("role"))) {
                        AdminWindow.main(user, db_user, db_item, db_store, db_inventory);
                    } else {
                        Window.main(user, db_user, db_item, db_store, db_inventory);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}