package db_classes;

public class DBItem {
    public static final String red_text = "\u001B[31m";
    private final Connector connector;

    public DBItem(Connector c) {
        connector = c;
    }

    public void create_item(Boolean is_admin, String name, double price) {
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to update.");
            return;
        }
        String s_price = String.valueOf(price);
        connector.create("ITEM", "name,price", "'"+name+"','"+s_price+"'");
    }

    public void delete_item(Boolean is_admin, int id) {
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to update.");
            return;
        }
        String s_id = String.valueOf(id);
        String item_exists = connector.read("ITEM", "id", "id", s_id);
        if(item_exists.isEmpty()) {
            System.out.println(red_text + "Item doesn't exist.");
            return;
        }
        connector.delete("ITEM", s_id);
    }

    public void set_price(Boolean is_admin, int id, int new_price) {
        if (!is_admin) {
            System.out.println(red_text + "Must be admin to update.");
            return;
        }
        String s_id = String.valueOf(id);
        String item_exists = connector.read("ITEM", "id", "id", s_id);
        if(item_exists.isEmpty()) {
            System.out.println(red_text + "Item doesn't exist.");
            return;
        }
        String s_new_price = String.valueOf(new_price);
        connector.update("ITEM", "price", s_new_price, s_id);
    }
}
