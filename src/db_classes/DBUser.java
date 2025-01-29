package db_classes;

import java.util.Dictionary;
import java.util.Hashtable;

public class DBUser {
    public static final String red_text = "\u001B[31m";

    public DBUser() {
        // init
    }

    public Boolean is_email_whitelisted(Connector connector, String email){
        String sql_value = connector.read("WL_EMAIL","email","email", email);
        return (sql_value.isEmpty());
    }

    public void create_account(Connector connector, String login, String name, String pwd) {
        int h_pwd = pwd.hashCode();
        String values = "'" + login + "','" + name + "','" + h_pwd + "',false";
        connector.create("USER", "email, name, password, is_admin", values);
        System.out.println("User " + login + " created successfully.");
    }

    public Dictionary<String, String> login(Connector connector, String login, String pwd) {
        Dictionary<String, String> infos = new Hashtable<>();
        infos.put("id","");
        infos.put("email","");
        infos.put("name","");
        infos.put("is_admin","");

        String sql_login = connector.read("USER", "email", "email", login);
        if(sql_login.isEmpty()) {
            System.out.println(red_text + "No user with this email registered.");
            return infos;
        }
        String sql_pwd = connector.read("USER", "password", "email", login);
        int p_sql_pwd = Integer.parseInt(sql_pwd);
        int h_pwd = pwd.hashCode();
        if(h_pwd != p_sql_pwd) {
            System.out.println(red_text + "Wrong password.");
            return infos;
        }
        String sql_id = connector.read("USER", "id", "email", login);
        String sql_name = connector.read("USER", "name", "email", login);
        String sql_role = connector.read("USER", "is_admin", "email", login);
        infos.put("id",sql_id);
        infos.put("email",sql_login);
        infos.put("name",sql_name);
        infos.put("is_admin",sql_role);
        return infos;
    }

    // mod = modifying user
    public void update_id(Boolean is_mod_admin) throws IllegalAccessException {
        if (!is_mod_admin) {
            throw new IllegalAccessException("Must be admin to update.");
        }
        // send DB update id req

    }

    public void update_email(Boolean is_mod_admin, int mod_id, int id) throws IllegalAccessException {
        if (!is_mod_admin || mod_id != id) {
            throw new IllegalAccessException("Must be admin or account's owner to update.");
        }
        // send DB update email req
    }

    public void update_name(Boolean is_mod_admin, int mod_id, int id) throws IllegalAccessException {
        if (!is_mod_admin || mod_id != id) {
            throw new IllegalAccessException("Must be admin or account's owner to update.");
        }
        // send DB update name req

    }

    public void update_role(Boolean is_mod_admin) throws IllegalAccessException {
        if (!is_mod_admin) {
            throw new IllegalAccessException("Must be admin to update.");
        }
        // send DB update role req

    }

    public void update_password(Boolean is_mod_admin, int mod_id, int id) throws IllegalAccessException {
        if (!is_mod_admin || mod_id != id) {
            throw new IllegalAccessException("Must be admin or account's owner to update.");
        }
        // send DB update pwd req

    }

    public void delete_user(Boolean is_mod_admin, int del_id, int id) throws IllegalAccessException {
        if (!is_mod_admin || del_id != id) {
            throw new IllegalAccessException("Must be admin to delete another account.");
        }
        // create delete request
        // send DB req
    }

    public String read_email(int id) {
        // create read req
        // send DB req
        return "";
    }

    public String read_name(int id) {
        // create read req
        // send DB req
        return "";
    }

    public Boolean read_role(int id) {
        // create read req
        // send DB req
        return false;
    }
}
