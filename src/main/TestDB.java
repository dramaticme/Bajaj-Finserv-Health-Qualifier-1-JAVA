package com.fastwebhook.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {
        String query = "SELECT * FROM Students"; // replace with your final SQL if you want

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("name") + " | " +
                        rs.getString("regno") + " | " +
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
