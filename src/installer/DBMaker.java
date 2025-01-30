package installer;

import java.sql.*;

public class DBMaker {
    private final Statement statement;

    public DBMaker(String url, String id, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("class used");
            Connection connect = DriverManager.getConnection(url, id, password);
            System.out.println("connection created");
            statement = connect.createStatement();
            System.out.println("statement created");
            statement.executeUpdate("create database java_mreq_db");
            System.out.println("Database created");
            statement.execute("use java_mreq_db");
            System.out.println("db used");

        }
        catch (SQLException | ClassNotFoundException e) {
            e.fillInStackTrace();
            throw new RuntimeException(e);
        }
        create_tables();
    }

    public void create_tables() {
        create_wl_email_table();
        create_user_table();
        create_store_table();
        create_store_user_table();
        create_inventory_table();
        create_item_table();
        create_inventory_item_table();
    }

    public void create_wl_email_table() {
        try {
            statement.execute("create table WL_EMAIL(" +
                    "email varchar(100) primary key )");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create_user_table() {
        try {
            statement.execute("create table USER(" +
                    "id int(5) primary key AUTO_INCREMENT," +
                    "email varchar(100) not null," +
                    "name varchar(50) not null," +
                    "password varchar(250) not null, " +
                    "is_admin bool not null)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create_store_table() {
        try {
            statement.execute("create table STORE(" +
                    "id int(5) primary key AUTO_INCREMENT," +
                    "name varchar(50) not null," +
                    "inventory_id int(5) not null )");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create_store_user_table() {
        try {
            statement.execute("create table STORE_USER(" +
                    "id int(5) primary key AUTO_INCREMENT," +
                    "store_id int(5) not null," +
                    "user_id int(5) not null )");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void create_inventory_table() {
        try {
            statement.execute("create table INVENTORY(" +
                    "id int(5) primary key AUTO_INCREMENT," +
                    "store_id int(5) not null," +
                    "constraint inventory_store_FK foreign key (store_id) references STORE(id))");
            statement.execute ("alter table STORE add constraint store_inventory_FK foreign key (inventory_id) references INVENTORY(id);");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void create_item_table() {
        try {
            statement.execute("create table ITEM(" +
                    "id int(5) primary key AUTO_INCREMENT," +
                    "name varchar(50) not null," +
                    "price float(10,2) not null)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void create_inventory_item_table() {
        try {
            statement.execute("create table INVENTORY_ITEM(" +
                    "id int(5) primary key AUTO_INCREMENT," +
                    "inventory_id int(5) not null," +
                    "item_id int(5) not null," +
                    "quantity int(10) not null," +
                    "capacity int(10) not null," +
                    "constraint inventory_item_inventory_FK foreign key (inventory_id) references INVENTORY(id)," +
                    "constraint inventory_item_item_FK foreign key (item_id) references ITEM(id) )");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
