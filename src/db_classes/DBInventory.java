package db_classes;

import java.util.ArrayList;
import java.util.List;

public class DBInventory {
    public static final String red_text = "\u001B[31m";
    private final Connector connector;

    public DBInventory(Connector c) {
        connector = c;
    }

    public void create_inventory(Boolean is_admin, int store_id) {
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to update.");
            return;
        }
        String s_store_id = String.valueOf(store_id);
        String store_exists = connector.read("STORE", "id", "id", s_store_id);
        String store_has_inv = connector.read("STORE", "inventory_id", "id", s_store_id);
        if(store_exists.isEmpty()) {
            System.out.println(red_text + "Store doesn't exists.");
            return;
        }
        if(!store_has_inv.isEmpty()) {
            System.out.println(red_text + "Store already has an inventory linked.");
            return;
        }
        connector.create("INVENTORY","store_id", s_store_id);
    }

    public void change_store(Boolean is_admin, int inv_id, int store_id) {
        String s_store_id = String.valueOf(store_id);
        String s_inv_id = String.valueOf(inv_id);

        if (!is_admin) {
            System.out.println(red_text + "Must be admin to update.");
            return;
        }
        String store_exists = connector.read("STORE", "id", "id", s_store_id);
        String inv_exists = connector.read("INVENTORY", "id", "id", s_inv_id);
        if(store_exists.isEmpty()) {
            System.out.println(red_text + "Store doesn't exists.");
            return;
        }
        if(inv_exists.isEmpty()) {
            System.out.println(red_text + "Inventory doesn't exists.");
            return;
        }

        connector.update("INVENTORY", "store_id", s_store_id, s_inv_id);
    }

    public void delete_inventory(Boolean is_admin, int id) {
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to update.");
            return;
        }
        String s_id = String.valueOf(id);
        String inv_exists = connector.read("INVENTORY", "id", "id", s_id);
        if(inv_exists.isEmpty()) {
            System.out.println(red_text + "Inventory doesn't exists.");
            return;
        }

        connector.delete("INVENTORY", s_id);
    }

    public void add_item(int user_id, int inv_id, int item_id, int quantity, int capacity) {
        String s_inv_id = String.valueOf(inv_id);
        String s_item_id = String.valueOf(item_id);
        String s_user_id = String.valueOf(user_id);
        String s_quantity = String.valueOf(quantity);
        String s_capacity = String.valueOf(capacity);
        String inv_exists = connector.read("INVENTORY", "id", "id", s_inv_id);
        String item_exists = connector.read("ITEM", "id", "id", s_item_id);
        if(inv_exists.isEmpty()) {
            System.out.println(red_text + "Inventory doesn't exist.");
            return;
        }
        if(item_exists.isEmpty()) {
            System.out.println(red_text + "Item doesn't exist.");
            return;
        }
        String user_store = connector.read("STORE_USER", "store_id", "user_id", s_user_id);
        String inv_store = connector.read("INVENTORY", "store_id", "id", s_inv_id);
        if(!user_store.equals(inv_store)) {
            System.out.println(red_text + "User not allowed to add any item to this store.");
            return;
        }
        connector.create("INVENTORY_ITEM", "inventory_id,item_id,quantity,capacity", s_inv_id+","+s_item_id+","+s_quantity+","+s_capacity);
    }

    public List<String> get_items(int user_id, int inv_id) {
        String s_inv_id = String.valueOf(inv_id);
        String s_user_id = String.valueOf(user_id);
        String user_store = connector.read("STORE_USER", "store_id", "user_id", s_user_id);
        String inv_store = connector.read("INVENTORY", "store_id", "id", s_inv_id);
        String inv_exists = connector.read("INVENTORY", "id", "id", s_inv_id);
        if(inv_exists.isEmpty()) {
            System.out.println(red_text + "Inventory doesn't exist.");
            return new ArrayList<>();
        }
        if(!user_store.equals(inv_store)) {
            System.out.println(red_text + "User not allowed to see items of this store.");
            return new ArrayList<>();
        }
        return connector.special_read("SELECT i.name FROM INVENTORY_ITEM ii JOIN ITEM i ON (i.id = ii.item_id)");
    }

    public void change_stock(int user_id, int inv_id, int item_id, int value_change) {
        String s_inv_id = String.valueOf(inv_id);
        String s_user_id = String.valueOf(user_id);
        String s_item_id = String.valueOf(item_id);
        String user_store = connector.read("STORE_USER", "store_id", "user_id", s_user_id);
        String inv_store = connector.read("INVENTORY", "store_id", "id", s_inv_id);
        String inv_exists = connector.read("INVENTORY", "id", "id", s_inv_id);
        String item_exists = connector.read("ITEM", "id", "id", s_item_id);
        if(inv_exists.isEmpty()) {
            System.out.println(red_text + "Inventory doesn't exist.");
            return;
        }
        if(item_exists.isEmpty()) {
            System.out.println(red_text + "Item doesn't exist.");
            return;
        }
        if(!user_store.equals(inv_store)) {
            System.out.println(red_text + "User not allowed to change items of this store.");
            return;
        }
        String value = connector.read("INVENTORY_ITEM", "quantity", "inventory_id", s_inv_id+" AND item_id = "+s_item_id); // test important
        int new_value = Integer.parseInt(value) + value_change;
        String s_new_value = String.valueOf(new_value);
        connector.special_update("UPDATE INVENTORY_ITEM SET quantity = " + s_new_value + " WHERE INVENTORY_ITEM.inventory_id = " + s_inv_id + " AND INVENTORY_ITEM.item_id = " + s_item_id);
    }
}
