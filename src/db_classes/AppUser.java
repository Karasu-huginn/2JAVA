package db_classes;

public class AppUser {
    private String email;
    private Boolean is_admin;
    private int id;
    private String name;

    public AppUser(String e, Boolean is_a, int i, String n) {
        email = e;
        is_admin = is_a;
        id = i;
        name = n;
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
