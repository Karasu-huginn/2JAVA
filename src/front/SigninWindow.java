package front;

import db_classes.Connector;
import db_classes.DBUser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SigninWindow {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sign Up");
        frame.setSize(350, 250);
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
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 80, 25);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(new Color(166, 123, 91));
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(120, 20, 180, 25);
        panel.add(nameText);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 60, 80, 25);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setForeground(new Color(166, 123, 91));
        panel.add(emailLabel);

        JTextField emailText = new JTextField(20);
        emailText.setBounds(120, 60, 180, 25);
        panel.add(emailText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 100, 80, 25);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(166, 123, 91));
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(120, 100, 180, 25);
        panel.add(passwordText);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(60, 150, 120, 30);
        signupButton.setFont(new Font("Arial", Font.BOLD, 14));
        signupButton.setBackground(new Color(166, 123, 91));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        signupButton.setBorder(BorderFactory.createLineBorder(new Color(130, 90, 60), 2));

        signupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signupButton.setBackground(new Color(195, 224, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                signupButton.setBackground(new Color(166, 123, 91));
            }
        });

        panel.add(signupButton);

        JButton signinButton = new JButton("Sign In");
        signinButton.setBounds(190, 150, 120, 30);
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
                LoginWindow.main(new String[]{});
            }
        });

        panel.add(signinButton);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String email = emailText.getText();
                String password = new String(passwordText.getPassword());

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String db_url = "jdbc:mysql://localhost:8889";
                String db_id = "root";
                String db_pwd = "root";
                Connector connector = new Connector(db_url, db_id, db_pwd);
                DBUser dbUser = new DBUser(connector);
                boolean success = dbUser.create_account(name, email, password);

                if (success) {
                    JOptionPane.showMessageDialog(panel, "Registration Successful!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(panel, "Registration Failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
