package db_classes;

import java.util.Dictionary;

public class AppUser {
    private String email;
    private Boolean is_admin;
    private int id;
    private String name;

    public AppUser(Dictionary<String, String> infos) {
        email = infos.get("email");
        is_admin = (Integer.parseInt(infos.get("is_admin")) != 0);
        id = Integer.parseInt(infos.get("id"));
        name = infos.get("name");
    }

    public Boolean get_is_admin() {
        return is_admin;
    }

    public int get_id() {
        return id;
    }

    public String get_name() {
        return name;
    }

    public String get_email() {
        return email;
    }
}
