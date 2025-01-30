package front;

import javax.swing.*;
import db_classes.AppUser;
import db_classes.DBUser;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OtherUsersWindow {
    private JFrame frame;
    private JButton goBackHomeButton;
    private AppUser currentUser;

    public OtherUsersWindow(AppUser user) {
        currentUser = user;
        frame = new JFrame("Other Users' Info");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(250, 235, 215));

        JLabel titleLabel = new JLabel("Other Users' Info", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(139, 90, 43));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(250, 235, 215));

        DBUser dbUser = new DBUser();
        List<UserInfo> otherUsersInfo = getOtherUsersInfo(dbUser, currentUser.get_id());

        if (otherUsersInfo.isEmpty()) {
            JLabel noUsersLabel = new JLabel("Sorry, we don't have any other users yet.", SwingConstants.CENTER);
            noUsersLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            noUsersLabel.setForeground(new Color(139, 90, 43));
            infoPanel.add(noUsersLabel);
        } else {
            for (UserInfo userInfo : otherUsersInfo) {
                String role = userInfo.isAdmin ? "Admin" : "User";
                infoPanel.add(new JLabel("Email: " + userInfo.email));
                infoPanel.add(new JLabel("Name: " + userInfo.name));
                infoPanel.add(new JLabel("Role: " + role));
                infoPanel.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scrollPane = new JScrollPane(infoPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        goBackHomeButton = new JButton("Go Back Home");
        goBackHomeButton.setBackground(Color.WHITE);  // White button
        goBackHomeButton.setForeground(new Color(139, 90, 43));
        goBackHomeButton.addActionListener(e -> goBackHome());
        frame.add(goBackHomeButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private List<UserInfo> getOtherUsersInfo(DBUser dbUser, int currentUserId) {
        List<UserInfo> usersInfo = new ArrayList<>();
        List<Integer> userIds = getAllUserIds(dbUser);

        for (int userId : userIds) {
            if (userId != currentUserId) {
                String email = dbUser.read_email(userId);
                String name = dbUser.read_name(userId);
                Boolean isAdmin = dbUser.read_role(userId);

                if (email != null && !email.isEmpty()) {
                    usersInfo.add(new UserInfo(email, name, isAdmin));
                }
            }
        }

        return usersInfo;
    }

    private List<Integer> getAllUserIds(DBUser dbUser) {
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);
        userIds.add(3);

        return userIds;
    }

    private static class UserInfo {
        String email;
        String name;
        Boolean isAdmin;

        UserInfo(String email, String name, Boolean isAdmin) {
            this.email = email;
            this.name = name;
            this.isAdmin = isAdmin;
        }
    }

    private void goBackHome() {
        new Window(currentUser);
        frame.dispose();
    }

    public static void main(String[] args) {
        AppUser user = new AppUser("test@example.com", true, 1, "John Doe");
        new OtherUsersWindow(user);
    }
}
