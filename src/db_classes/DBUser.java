package db_classes;

import java.util.Dictionary;
import java.util.Hashtable;

public class DBUser {
    public static final String red_text = "\u001B[31m";
    Connector connector;

    public DBUser(Connector c) {
        connector = c;
    }

    public Boolean is_email_whitelisted(String email){
        String sql_value = connector.read("WL_EMAIL","email","email", "'"+email+"'");
        return (!sql_value.isEmpty());
    }

    public void whitelist_email(Boolean is_mod_admin, String email) {
        if (!is_mod_admin) {
            System.out.println(red_text + "Must be admin to update.");
            return;
        }
        String email_available = connector.read("WL_EMAIL", "email", "email", "'"+email+"'");
        if(!email_available.isEmpty()) {
            System.out.println(red_text + "This id is already in use.");
            return;
        }
        connector.create("WL_EMAIL", "email", "'"+email+"'");
    }

    public Boolean create_account(String login, String name, String pwd) {
        if(!is_email_whitelisted(login)) {
            System.out.println(red_text + "Email is not whitelisted.");
            return false;
        }
        int h_pwd = pwd.hashCode();
        String values = "'" + login + "','" + name + "','" + h_pwd + "',false";
        connector.create("USER", "email, name, password, is_admin", values);
        System.out.println("User " + login + " created successfully.");
        return true;
    }

    public Dictionary<String, String> login(String login, String pwd) {
        Dictionary<String, String> infos = new Hashtable<>();
        infos.put("id","");
        infos.put("email","");
        infos.put("name","");
        infos.put("is_admin","");

        String sql_login = connector.read("USER", "email", "email", "'"+login+"'");
        if(sql_login.isEmpty()) {
            System.out.println(red_text + "No user with this email registered.");
            return infos;
        }
        String sql_pwd = connector.read("USER", "password", "email", "'"+login+"'");
        int p_sql_pwd = Integer.parseInt(sql_pwd);
        int h_pwd = pwd.hashCode();
        if(h_pwd != p_sql_pwd) {
            System.out.println(red_text + "Wrong password.");
            return infos;
        }
        String sql_id = connector.read("USER", "id", "email", "'"+login+"'");
        String sql_name = connector.read("USER", "name", "email", "'"+login+"'");
        String sql_role = connector.read("USER", "is_admin", "email", "'"+login+"'");
        infos.put("id",sql_id);
        infos.put("email",sql_login);
        infos.put("name",sql_name);
        infos.put("is_admin",sql_role);
        return infos;
    }

    // mod = modifying user
    public void update_id(Boolean is_mod_admin, int id, String new_id) {
        if (!is_mod_admin) {
            System.out.println(red_text + "Must be admin to update.");
            return;
        }
        String s_id = String.valueOf(id);
        String id_availability = connector.read("USER", "id", "id", new_id);
        if(id_availability.isEmpty()) {
            connector.update("USER","id", new_id,s_id);
        }
        else {
            System.out.println(red_text + "This id is already in use.");
        }

    }

    public void update_email(Boolean is_mod_admin, int mod_id, int id, String email) {
        if (!is_mod_admin && mod_id != id) {
            System.out.println(red_text + "Must be admin or account's owner to update.");
            return;
        }
        String s_id = String.valueOf(id);
        String id_availability = connector.read("USER", "email", "email", "'"+email+"'");
        if(id_availability.isEmpty()) {
            connector.update("USER","email", "'"+email+"'",s_id);
        }
        else {
            System.out.println(red_text + "This email is already in use.");
        }
    }

    public void update_name(Boolean is_mod_admin, int mod_id, int id, String name) {
        if (!is_mod_admin && mod_id != id) {
            System.out.println(red_text + "Must be admin or account's owner to update.");
            return;
        }
        String s_id = String.valueOf(id);
        connector.update("USER","name", "'"+name+"'", s_id);

    }

    public void update_role(Boolean is_mod_admin, int id, Boolean role) {
        if (!is_mod_admin) {
            System.out.println(red_text + "Must be admin to update.");
            return;
        }
        String s_id = String.valueOf(id);
        String s_role = role ? "1" : "0";
        connector.update("USER","is_admin", s_role, s_id);

    }

    public void update_password(Boolean is_mod_admin, int mod_id, int id, String pwd) {
        if (!is_mod_admin && mod_id != id) {
            System.out.println(red_text + "Must be admin or account's owner to update.");
            return;
        }
        String s_id = String.valueOf(id);
        int h_pass = pwd.hashCode();
        pwd = String.valueOf(h_pass);
        connector.update("USER","password", "'"+pwd+"'", s_id);
    }

    public void delete_user(Boolean is_mod_admin, int del_id, int id) {
        if (!is_mod_admin && del_id != id) {
            System.out.println(red_text + "Must be admin to delete another account.");
            return;
        }
        String s_id = String.valueOf(id);
        connector.delete("USER", s_id);
    }

    public String read_email(int id) {
        String s_id = String.valueOf(id);
        String sql_value = connector.read("USER", "email", "id", s_id);
        return sql_value;
    }

    public String read_name(int id) {
        String s_id = String.valueOf(id);
        String sql_value = connector.read("USER", "email", "id", s_id);
        return sql_value;
    }

    public Boolean read_role(int id) {
        String s_id = String.valueOf(id);
        String sql_value = connector.read("USER", "email", "id", s_id);
        return Boolean.valueOf(sql_value);
    }
}
