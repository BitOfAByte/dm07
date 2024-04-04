package me.tjens23.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static me.tjens23.Main.postgres;
public class SQL {
    private PreparedStatement statement;

    public void createTable() {
        try {
            statement = postgres.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS quest1 (id SERIAL PRIMARY KEY, name VARCHAR(255), cpr VARCHAR(20) UNIQUE NOT NULL)");
            statement.executeUpdate();
            System.out.println("Table created successfully");
        } catch (Exception e) {
            System.out.println("Failed to create table users" + e.getCause() + e.getMessage());
        }
    }

    public void insertUser(String name, String cpr) {
        try {
            statement = postgres.getConnection().prepareStatement("INSERT INTO quest1 (name, cpr) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, cpr);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to insert user" + e.getCause() + e.getMessage());
        }
    }

    public void printUsersToConsole() {
        try {
            statement = postgres.getConnection().prepareStatement("SELECT * FROM quest1");
            statement.executeQuery();
            while(statement.getResultSet().next()) {
                System.out.println("ID: " + statement.getResultSet().getInt("id") + " Name: " + statement.getResultSet().getString("name") + " CPR: " + statement.getResultSet().getString("cpr"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to print users to console" + e.getCause() + e.getMessage());
        }
    }


    public void executeQuery(String query) {
        try {
            statement = postgres.getConnection().prepareStatement(query);
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to execute query" + e.getCause() + e.getMessage());
        }
    }
}
