import db_classes.*;
import installer.DBMaker;

import java.sql.SQLException;
import java.util.Dictionary;

public class Main {
    public static final String red_text = "\u001B[31m";
    public static final String green_text = "\u001B[32m";
    public static final String reset_text = "\u001B[0m";

    public static void main(String[] args) {
        String db_url = "jdbc:mysql://localhost:3306";
        String db_id = "root";
        String db_pwd = "admin";

        Connector connector = new Connector(db_url, db_id, db_pwd);

        try {
            connector.db_connect();  // Ensure this method throws SQLException if needed
        }
        catch (ClassNotFoundException e) {
            System.out.println(red_text + "ERROR ON DRIVER CLASS. PLEASE ENSURE DRIVER IS ON PATH"+reset_text);
        }
        catch (SQLException e) {
            System.out.println(red_text + "ERROR ON DB CONNECTION. TRYING TO CREATE A DB..."+reset_text);
            new DBMaker(db_url, db_id, db_pwd);
            System.out.println(green_text + "DB CREATED."+reset_text);
            throw new RuntimeException(e);
        }

        DBUser db_user = new DBUser(connector);
        DBItem db_item = new DBItem(connector);
        DBStore db_store = new DBStore(connector);
        DBInventory db_inventory = new DBInventory(connector);
        String password = "motdepasse";
        String login = "admin@example.com";
        Dictionary<String, String> user_infos = db_user.login(login, password);
        AppUser app_user = new AppUser(user_infos);

        System.out.println(green_text+"Connected."+reset_text);
        //db_user.whitelist_email(app_user.get_is_admin(),"user@example.com");
        //db_user.create_account("user@example.com", "michel","motdepasse");
        //db_store.create_store(app_user.get_is_admin(),"Paris");
        //db_inventory.create_inventory(app_user.get_is_admin(),1);
        db_store.add_inventory(app_user.get_is_admin(),1,1);
        //db_user.update_name(app_user.get_is_admin(),app_user.get_id(),2,"Pierre");
        //db_item.create_item(app_user.get_is_admin(),"Transistor", 5.99);
        //db_store.add_employee(app_user.get_is_admin(),2,1);
        //db_inventory.add_item(2,1,1,5,10);
        System.out.println("Done.");
    }
}
