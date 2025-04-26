package com.sdc.tradeo.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConfig {
    public static void main(String[] args) {
        String url = "jdbc:mysql://root:mcCxSFFbXmltEYSsbWTcaNKoefUWplPj@yamabiko.proxy.rlwy.net:26630/railway";
        String user = "root";
        String password = "mcCxSFFbXmltEYSsbWTcaNKoefUWplPj";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println(" Database Connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
