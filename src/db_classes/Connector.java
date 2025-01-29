package db_classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> read(String table, String column) {
        String sql_req = "SELECT " + column + " FROM " + table;
        String column_name;
        String value;
        List<String> outputs = new ArrayList<>();
        try {
            ResultSet result_set = statement.executeQuery(sql_req);
            java.sql.ResultSetMetaData rsmd = result_set.getMetaData();
            while (result_set.next()) {
                for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                    column_name = rsmd.getColumnName(i);
                    value = result_set.getString(column_name);
                    outputs.add(value);
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException(e);
        }
        return outputs;
    }

    public String read(String table, String column, String filter, String filter_value) {
        String sql_req = "SELECT " + column + " FROM " + table + " WHERE " + filter + " = " + filter_value;
        String column_name;
        String value = "QUERY_ERROR";
        try {
            ResultSet result_set = statement.executeQuery(sql_req);
            java.sql.ResultSetMetaData rsmd = result_set.getMetaData();
            if(!result_set.next()) {
                return "";
            }
            else {
                do {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        column_name = rsmd.getColumnName(i);
                        value = result_set.getString(column_name);
                    }
                } while (result_set.next());
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new RuntimeException(e);
        }
        return value;
    }

    public void create(String table, String columns, String values) {
        String sql_req = "INSERT INTO " + table + "(" + columns + ") VALUES (" + values + ")";
        try {
            statement.execute(sql_req);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String table, String column, String value, String id) {
        String sql_req = "UPDATE "+ table +" SET " + column + " = '" + value + "' WHERE " + table + ".id = " + id;
        try {
            statement.execute(sql_req);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String table, String id) {
        String sql_req = "DELETE FROM " + table + " WHERE " + table + ".id = " + id + ";";
        try {
            statement.execute(sql_req);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
