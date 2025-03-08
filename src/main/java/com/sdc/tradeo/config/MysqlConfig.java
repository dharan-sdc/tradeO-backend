package com.sdc.tradeo.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConfig {
    public static void main(String[] args) {
        String url = "jdbc:mysql://root:sZqnvERGtjQUHPlwNLTOTwgOUcEmEmXP@shuttle.proxy.rlwy.net:24957/railway";
        String user = "root";
        String password = "sZqnvERGtjQUHPlwNLTOTwgOUcEmEmXP";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Railway Database Connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
