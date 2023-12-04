package com.app.coding.db;

import com.app.coding.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbService {

    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/bbdb";
    public static final String JDBC_USER = "bb";
    public static final String JDBC_PASSWORD = "jellyfish";
    public static final String JDBC_DRIVER = "org.postgresql.Driver";

    public static final String SELECT_ALL_USERS = "select * from crazy_users";
    public static final String INSERT_NEW_USER = "insert into crazy_users (first_name, last_name, email) values (?, ?, ?)";
    public static final String DELETE_USER = "delete from crazy_users where id = ? ";

    public static List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS)) {
            // Extract data from result set
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Создаем нового юзера в базе
    public static void createNewUser(User user){
        List<User> users = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(INSERT_NEW_USER)){
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление юзера из базы
    public static void removeUserById(String id) {
        try(Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(DELETE_USER)){
            stmt.setLong(1, Long.parseLong(id));
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
