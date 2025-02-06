package front;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import db_classes.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ManageUsersWindow {
    private JFrame frame;
    private JTable usersTable;
    private JButton createUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;
    private AppUser currentUser;
    private DBUser db_user;
    private DBItem db_item;
    private DBStore db_store;
    private DBInventory db_inventory;

    public ManageUsersWindow(AppUser user, DBUser db_u, DBItem db_it, DBStore db_s, DBInventory db_in) {
        this.currentUser = user;
        DBUser db_user = db_u;
        DBItem db_item = db_it;
        DBStore db_store = db_s;
        DBInventory db_inventory = db_in;
        frame = new JFrame("Manage Users");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(250, 235, 215));

        String[] columnNames = {"ID", "Email", "Username", "Role", "Store"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        usersTable = new JTable(model);
        loadUsers(model);

        JScrollPane scrollPane = new JScrollPane(usersTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBackground(new Color(250, 235, 215));

        createUserButton = new JButton("Create User");
        createUserButton.setBackground(Color.WHITE);
        createUserButton.setForeground(new Color(139, 90, 43));
        createUserButton.addActionListener(e -> openCreateUserWindow());
        buttonPanel.add(createUserButton);

        updateUserButton = new JButton("Update User");
        updateUserButton.setBackground(Color.WHITE);
        updateUserButton.setForeground(new Color(139, 90, 43));
        updateUserButton.addActionListener(e -> openUpdateUserWindow());
        buttonPanel.add(updateUserButton);

        deleteUserButton = new JButton("Delete User");
        deleteUserButton.setBackground(Color.WHITE);
        deleteUserButton.setForeground(new Color(139, 90, 43));
        deleteUserButton.addActionListener(e -> deleteUser());
        buttonPanel.add(deleteUserButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void loadUsers(DefaultTableModel model) {
        List<AppUser> users = db_user.get_other_users();
        for (AppUser user : users) {
            if (!user.get_id().equals(currentUser.get_id()) || currentUser.get_is_admin()) {
                model.addRow(new Object[]{
                        user.get_id(),
                        user.get_email(),
                        user.get_name(),
                        user.get_is_admin() ? "Admin" : "User",
                        db_user.get_user_store()
                });
            }
        }
    }


    private void openCreateUserWindow() {
        //new CreateUserWindow(currentUser);
    }

    private void openUpdateUserWindow() {
        //new UpdateUserWindow(currentUser, getSelectedUserId());
    }

    private void deleteUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) usersTable.getValueAt(selectedRow, 0);
            if (userId != currentUser.get_id() && !db_user.read_role(userId)) {
                db_user.delete_user(currentUser.get_is_admin(), currentUser.get_id(), userId);
                ((DefaultTableModel) usersTable.getModel()).removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Admins cannot delete another admin or themselves.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int getSelectedUserId() {
        int selectedRow = usersTable.getSelectedRow();
        return selectedRow != -1 ? (int) usersTable.getValueAt(selectedRow, 0) : -1;
    }
}
