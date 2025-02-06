import db_classes.AppUser;
import db_classes.Connector;
import db_classes.DBUser;
import installer.DBMaker;

import java.util.Dictionary;

public class Main {
    public static final String red_text = "\u001B[31m";
    public static final String green_text = "\u001B[32m";

    public static void main(String[] args) {
        String db_url = "jdbc:mysql://localhost:3306";
        String db_id = "root";
        String db_pwd = "admin";

        Connector connector = new Connector(db_url, db_id, db_pwd);

        try {
            connector.db_connect();  // Ensure this method throws SQLException if needed
        }
        catch (ClassNotFoundException e) {
            System.out.println(red_text + "ERROR ON DB CONNECTION. TRYING TO CREATE A DB...");
            new DBMaker(db_url, db_id, db_pwd);
            System.out.println(green_text + "DB CREATED.");
        }

        DBUser db_user = new DBUser(connector);
        String password = "admin";
        String login = "admin1@test.com";
        String name = "admin1";
        db_user.create_account(login, name, password);
        Dictionary<String, String> user_infos = db_user.login(login, password);
        AppUser app_user = new AppUser(user_infos);
    }
}
