package db_classes;

import java.sql.*;

public class Connector {
    Statement statement;
    String url;
    String id;
    String password;

    public Connector(String u, String i, String pwd) {
        url = u+"/java_mreq_db";
        id = i;
        password = pwd;
    }

    public void db_connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, id, password);
        statement = connection.createStatement();
    }

    public Boolean is_connected() {
        // send DB simple read
        // check if DB responds
        return true;
    }

    public String read(String table) {
        String sql_req = "SELECT * FROM " + table;

        try {
            statement.executeQuery(sql_req);
        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException(e);
        }
        return "";
    }

    public String read(String table, String filter, String filter_value) {
        String sql_req = "SELECT * FROM " + table + " WHERE " + filter + " = " + filter_value;

        try {
            statement.executeQuery(sql_req);
        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException(e);
        }
        return "";
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
