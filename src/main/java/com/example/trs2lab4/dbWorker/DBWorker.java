package com.example.trs2lab4.dbWorker;

import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.entity.Manufacturer;
import com.example.trs2lab4.entity.ProductManufacturerCategory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class DBWorker {
//    static {
//        try {
//            // Для MySQL:
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//        } catch (ClassNotFoundException e) {
//            System.err.println("Драйвер не найден!");
//            e.printStackTrace();
//        }
//    }
    private static final String URL = "jdbc:mysql://localhost:3306/trs2_lab";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public List<ProductManufacturerCategory> fetchProductsWithCondition(String condition) {
        List<ProductManufacturerCategory> list = new ArrayList<>();
        System.out.println("""
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
                   """ + " " + condition);
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
                   """ + " " + condition)) {
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

    public List<ProductManufacturerCategory> fetchAllProducts() {
        return fetchProductsWithCondition("");
    }

    public List<ProductManufacturerCategory> fetchProductsByCategoryId(Long id) {
        return fetchProductsWithCondition("where p.category_id = " + id);
    }
    public List<ProductManufacturerCategory> fetchProductsByManufacturerId(Long id) {
        return fetchProductsWithCondition("where p.manufacturer_id = " + id);
    }
    public List<ProductManufacturerCategory> fetchProductsByPriceBetween(BigDecimal from, BigDecimal to) {
        return fetchProductsWithCondition(String.format(java.util.Locale.US, "WHERE p.price BETWEEN %.2f AND %.2f", from, to));
    }

    public void addProduct(ProductManufacturerCategory product) {
        String sql = "INSERT INTO product (name, price, category_id, manufacturer_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setBigDecimal(2, product.getPrice());
            pstmt.setLong(3, product.getCategoryId());
            pstmt.setLong(4, product.getManufacturerId());
            pstmt.executeUpdate();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateProduct(ProductManufacturerCategory product) {
        String sql = """
                 UPDATE product 
                 SET name = ?, price = ?, category_id = ?, manufacturer_id = ? 
                 WHERE id = ?
                 """;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setBigDecimal(2, product.getPrice());
            pstmt.setLong(3, product.getCategoryId());
            pstmt.setLong(4, product.getManufacturerId());
            pstmt.setLong(5, product.getId());
            pstmt.executeUpdate();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteProduct(Long id) {
        String sql = "delete from product where id = " + id;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Category> fetchAllCategoriesWithCondition(String condition) {
        List<Category> list = new ArrayList<>();
        // Тут логіка підключення до БД як у методичці
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * from category c " + condition)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                Category cat = new Category(id, name);
                list.add(cat);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Category> fetchAllCategories() {
        return fetchAllCategoriesWithCondition("");
    }

    public Category findCategoryById(Long id) {
        return fetchAllCategoriesWithCondition("where c.id = " + id).stream().findFirst().orElseThrow(
                () -> new RuntimeException("category " + id + " did not found")
        );
    }

    public List<Manufacturer> fetchAllManufacturersWithCondition(String condition) {
        System.out.println("SELECT * from manufacturer m " + condition);
        List<Manufacturer> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * from manufacturer m " + condition)) {
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

    public List<Manufacturer> fetchAllManufacturers() {
        return fetchAllManufacturersWithCondition("");
    }
    public Manufacturer findManufacturerById(Long id) {
        return fetchAllManufacturersWithCondition("where m.id = " + id).stream().findFirst().orElseThrow(
                () -> new RuntimeException("Manufacturer " + id + " did not found")
        );
    }
}
