package dev.aarow.parkour.utility.data;

import dev.aarow.parkour.ParkourPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAuthentication {

    private String host;
    private int port;
    private String database;
    private boolean authentication;
    private String username;
    private String password;

    public DatabaseAuthentication(){
        this.host = ParkourPlugin.getInstance().getConfig().getString("MYSQL.HOST");
        this.port = ParkourPlugin.getInstance().getConfig().getInt("MYSQL.PORT");
        this.database = ParkourPlugin.getInstance().getConfig().getString("MYSQL.DATABASE");
        this.authentication = ParkourPlugin.getInstance().getConfig().getBoolean("MYSQL.AUTHENTICATION.ENABLED");
        this.username = ParkourPlugin.getInstance().getConfig().getString("MYSQL.AUTHENTICATION.USERNAME");
        this.password = ParkourPlugin.getInstance().getConfig().getString("MYSQL.AUTHENTICATION.PASSWORD");
    }

    public Connection init(){
        try {
            if(authentication){
                return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            }else {
                return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
