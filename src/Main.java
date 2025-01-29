import db_classes.AppUser;
import db_classes.Connector;
import db_classes.DBUser;
import installer.DBMaker;

import java.sql.SQLException;

public class Main {
    public static final String red_text = "\u001B[31m";
    public static final String green_text = "\u001B[32m";
    public static void main(String[] args) {
        String db_url = "jdbc:mysql://localhost:3306"; //remove _test
        String db_id = "root";
        String db_pwd = "admin";

        Connector connector = new Connector(db_url,db_id, db_pwd);
        try {
            connector.db_connect();
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(red_text + "ERROR ON DB CONNECTION. TRYING TO CREATE A DB...");
            new DBMaker(db_url, db_id, db_pwd);
            System.out.println(green_text + "DB CREATED.");
        }
        DBUser db_user = new DBUser();
        String password = "";
        String login = "";
        int user_id = db_user.login(login, password);
        AppUser app_user = new AppUser(
                db_user.read_email(user_id),
                db_user.read_role(user_id),
                user_id,
                db_user.read_name(user_id)
        );

    }
}