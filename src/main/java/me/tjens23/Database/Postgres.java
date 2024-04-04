package me.tjens23.Database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Postgres {
    private Connection connection;
    private final Dotenv dotenv = Dotenv.load();

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.out.println("Something went wrong while checking if the connection is open" + e.getCause() + e.getMessage());
            return false;
        }
    }

    public void connect() {
        if(!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://" + dotenv.get("db_host") + ":" + dotenv.get("db_port") + "/" + dotenv.get("db_name"), dotenv.get("db_user"), dotenv.get("db_password"));
                System.out.println("Connected to postgres database");
            } catch (SQLException e) {
                System.out.println("Failed to connected to postgres database + " + e.getCause() + e.getMessage());
            }
        }
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
                System.out.println("Disconnected from postgres database");
            } catch (SQLException e) {
                System.out.println("Failed to disconnect from postgres database + " + e.getCause() + e.getMessage());
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
