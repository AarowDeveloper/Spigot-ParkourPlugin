package dev.aarow.parkour.managers.impl;

import dev.aarow.parkour.managers.Manager;
import dev.aarow.parkour.utility.data.DatabaseAuthentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager extends Manager {

    private Connection connection;
    private Statement statement;

    @Override
    public void setup() {
        try{
            synchronized (this) {
                Class.forName("com.mysql.jdbc.Driver");

                connection = new DatabaseAuthentication().init();

                this.statement = connection.createStatement();
            }
        }catch(Exception e){

        }

        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS parkours (" +
                    "NAME TEXT NOT NULL, " +
                    "CHECKPOINTS TEXT NOT NULL, " +
                    "PRIMARY KEY (NAME(255))" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}