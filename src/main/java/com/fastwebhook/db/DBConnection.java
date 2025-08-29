package com.fastwebhook.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnection {
    
    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/students}")
    private String url;
    
    @Value("${spring.datasource.username:root}")
    private String user;
    
    @Value("${spring.datasource.password}")
    private String password;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}