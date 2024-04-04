package me.tjens23;

import me.tjens23.Database.Postgres;
import me.tjens23.Database.SQL;

public class Main {
    public static Postgres postgres = new Postgres();
    private static final SQL sql = new SQL();
    public static void main(String[] args) {
        if(!postgres.isConnected()) {
            postgres.connect();
        }

        sql.createTable();
        sql.insertUser("John Doe", "123456-7891");
        sql.insertUser("Jane Doe", "098765-4321");
        sql.insertUser("Alice Doe", "123456-7890");
        sql.printUsersToConsole();
    }
}