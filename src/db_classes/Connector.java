package db_classes;

import java.sql.*;

public class Connector {
    // DB data vars
    Connection connection;
    String url;
    String id;
    String password;

    public Connector(String u, String i, String pwd) {
        url = u+"/java_mreq_db_test";
        id = i;
        password = pwd;
    }

    public void db_connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, id, password);
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
