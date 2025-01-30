package db_classes;

import java.util.ArrayList;
import java.util.List;

public class DBStore {
    public static final String red_text = "\u001B[31m";
    private final Connector connector;

    public DBStore(Connector c) {
        connector = c;
    }

    public void add_employee(Boolean is_admin, int user_id, int store_id) {
        String s_user_id = String.valueOf(user_id);
        String s_store_id = String.valueOf(store_id);
        String user_has_store = connector.read("STORE_USER","id", "id", s_user_id);
        String store_exists = connector.read("STORE", "id", "id", s_store_id);
        String user_exists = connector.read("USER", "id", "id", s_user_id);
        if(!user_has_store.isEmpty()) {
            System.out.println(red_text + "User already in a store.");
            return;
        }
        if(store_exists.isEmpty()) {
            System.out.println(red_text + "This store doesn't exists.");
            return;
        }
        if(user_exists.isEmpty()) {
            System.out.println(red_text + "This store doesn't exists.");
            return;
        }
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to create a store.");
            return;
        }

        connector.create("STORE_USER", "store_id, user_id",s_store_id+","+s_user_id);
    }

    public void create_store(Boolean is_admin, int id, String name) {
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to create a store.");
            return;
        }
        connector.create("STORE", "name", "'"+name+"'");
    }

    public void create_store(Boolean is_admin, String name, int inv_id) {
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to create a store.");
            return;
        }

        String s_inv_id = String.valueOf(inv_id);
        String id_exists = connector.read("INVENTORY", "id", "id", s_inv_id);
        String inventory_used = connector.read("STORE", "id", "inventory_id", s_inv_id);
        if(id_exists.isEmpty()) {
            System.out.println(red_text + "No inventory found with this id.");
            return;
        }
        if(!inventory_used.isEmpty()) {
            System.out.println(red_text + "A store is already linked to this inventory.");
            return;
        }

        connector.create("STORE", "name, inventory_id", "'"+name+"',"+s_inv_id);
    }

    public void add_inventory(Boolean is_admin, int id, int inv_id) {
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to change a store.");
            return;
        }

        String s_id = String.valueOf(id);
        String s_inv_id = String.valueOf(inv_id);
        String store_exists = connector.read("STORE", "id", "id",s_id);
        String inv_exists = connector.read("INVENTORY", "id", "id",s_id);
        String inventory_used = connector.read("STORE", "id", "inventory_id", s_inv_id);
        if(store_exists.isEmpty()) {
            System.out.println(red_text + "No store found with this id.");
            return;
        }
        if(inv_exists.isEmpty()) {
            System.out.println(red_text + "No inventory found with this id.");
            return;
        }
        if(!inventory_used.isEmpty()) {
            System.out.println(red_text + "A store is already linked to this inventory.");
            return;
        }

        connector.update("STORE", "inventory_id", s_inv_id, s_id);
    }

    public List<String> get_employees(Boolean is_admin, int user_id, int store_id) {
        String s_user_id = String.valueOf(user_id);
        String user_in_store = connector.read("STORE_USER", "store_id", "user_id", s_user_id);
        if(!is_admin && user_in_store.equals(String.valueOf(store_id))) {
            System.out.println(red_text + "Must be admin to see employees of another store.");
            return new ArrayList<>();
        }
        return connector.special_read("SELECT u.name FROM STORE_USER su JOIN USER u ON (u.id = su.user_id)");
    }

    public void delete_store(Boolean is_admin, int id) {
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to delete a store.");
            return;
        }
        String s_id = String.valueOf(id);
        connector.delete("STORE", s_id);
    }
}
