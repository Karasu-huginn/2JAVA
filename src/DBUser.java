public class DBUser {

    public DBUser() {
        // init
    }

    public Boolean is_email_whitelisted(){
        // send DB read with email
        // check for matching emails
        return true;
    }

    public void create_account(String login, String name, String pwd) {
        // hash password
        // send DB create with infos
        // display (return ?) success/error
    }

    public void login(String login, String pwd) {
        // ask DB with login and pwd
        // create infos dict & return
        // handle login fail
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
