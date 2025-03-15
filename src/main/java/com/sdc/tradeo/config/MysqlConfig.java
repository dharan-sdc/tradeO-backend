package com.sdc.tradeo.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConfig {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tradeo_db";
        String user = "root";
        String password = "01010100";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println(" Database Connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
