package com.example.trs2lab4.dbWorker;

import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.entity.Manufacturer;
import com.example.trs2lab4.entity.ProductManufacturerCategory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DBWorker {
    static {
        try {
            // Для MySQL:
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.err.println("Драйвер не найден!");
            e.printStackTrace();
        }
    }
    private static final String URL = "jdbc:mysql://localhost:3306/trs2_lab";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public List<ProductManufacturerCategory> fetchAllProducts() {
        List<ProductManufacturerCategory> list = new ArrayList<>();
        // Тут логіка підключення до БД як у методичці
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
          """
          SELECT
           p.id AS product_id,
           p.name AS product_name,
           p.price AS product_price,
           c.id AS category_id,
           c.name AS category_name,
           m.id AS man_id,
           m.name AS man_name
       FROM product p
       JOIN category c ON c.id = p.category_id
       JOIN manufacturer m ON m.id = p.manufacturer_id
        """)) {
            while (rs.next()) {
                long productId = rs.getLong("product_id");
                String productName = rs.getString("product_name");
                BigDecimal price = rs.getBigDecimal("product_price");

                long categoryId = rs.getLong("category_id");
                String categoryName = rs.getString("category_name");

                long manufacturerId = rs.getLong("man_id");
                String manufacturerName = rs.getString("man_name");

                ProductManufacturerCategory prod = new ProductManufacturerCategory(productId, productName, price,
                        categoryId, categoryName, manufacturerId, manufacturerName);

                list.add(prod);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Category> fetchAllCategories() {
        List<Category> list = new ArrayList<>();
        // Тут логіка підключення до БД як у методичці
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     """
                         SELECT * from category
                     """)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                Category cat = new Category(id, name);
                list.add(cat);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Manufacturer> fetchAllManufacturers() {
        List<Manufacturer> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     """
                         SELECT * from manufacturer
                     """)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                Manufacturer man = new Manufacturer(id, name, country);
                list.add(man);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
