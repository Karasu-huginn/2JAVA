package db_classes;

public class Connector {
    // DB data vars

    public Connector() {
        // init
    }

    public Boolean is_connected() {
        // send DB simple read
        // check if DB responds
        return true;
    }

    public void read(String table) {
        String sql_req = "SELECT * FROM " + table;
        // send request in DB
    }

    public void read(String table, String filter, String filter_value) {
        String sql_req = "SELECT * FROM " + table + " WHERE " + filter + " = " + filter_value;
        // send request in DB
    }

    public void create() {
        // create sql_req
    }

    public void update() {
        // create sql_req
    }

    public void delete() {
        // create sql_req
    }

}
