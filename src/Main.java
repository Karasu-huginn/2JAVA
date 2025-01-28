import db_classes.AppUser;
import db_classes.Connector;
import db_classes.DBUser;

public class Main {
    public static void main(String[] args) {
        Connector connector = new Connector();
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