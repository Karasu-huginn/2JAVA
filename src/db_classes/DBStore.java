package db_classes;

public class DBStore {
    public static final String red_text = "\u001B[31m";
    private final Connector connector;

    public DBStore(Connector c) {
        connector = c;
    }

    public void add_employee(Boolean is_admin, int emp_id, int store_id) {

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
        if(id_exists.isEmpty()) {
            System.out.println(red_text + "No inventory found with this id.");
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
        if(store_exists.isEmpty()) {
            System.out.println(red_text + "No store found with this id.");
            return;
        }
        if(inv_exists.isEmpty()) {
            System.out.println(red_text + "No inventory found with this id.");
            return;
        }

        connector.update("STORE", "inventory_id", s_inv_id, s_id);
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
