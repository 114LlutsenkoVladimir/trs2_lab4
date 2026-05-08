package com.example.trs2lab4.controller.dbWorker;

import java.sql.*;
import java.util.ArrayList;

public class DBWorker {
    public List<Product> fetchAllProducts() {
        List<Product> list = new ArrayList<>();
        // Тут логіка підключення до БД як у методичці
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM product")) {
            while (rs.next()) {
                list.add(new Product(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
