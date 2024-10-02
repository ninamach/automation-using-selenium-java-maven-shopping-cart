package com.hackerrank.selenium.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
  private static final String CREATE_TBL_SQL =
          "CREATE TABLE products "
                  + "(name TEXT NOT NULL,"
                  + " img TEXT NOT NULL, "
                  + " status TEXT NOT NULL, "
                  + " price INTEGER NOT NULL)";

  // create connection to sqlite database
  public static Connection getConnection() {
    Connection connection;
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:shopping_cart.db");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return connection;
  }

  // clear database
  public static void init() {
    Connection connection = getConnection();
    Statement statement = null;
    try {
      statement = connection.createStatement();
      statement.executeUpdate("DROP TABLE IF EXISTS products");
      statement.executeUpdate(CREATE_TBL_SQL);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        statement.close();
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  // update the product product.status using product.name
  public static void deleteProducts() {
    Connection connection = getConnection();
    Statement statement = null;
    try {
      statement = connection.createStatement();
      String sqlInsert = "delete from products";
      int numDelete = statement.executeUpdate(sqlInsert);
      System.out.println(numDelete + " product(s) is/are deleted.");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        statement.close();
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  // inset product
  public static void insertProduct(Product product) {
    Connection connection = getConnection();
    Statement statement = null;
    try {
      statement = connection.createStatement();
      String sqlInsert = String.format("insert into products (name, img, status, price) values ('%s', '%s', '%s', %d)",
              product.name, product.img, product.status, product.price);
      int numInsert = statement.executeUpdate(sqlInsert);
      if (numInsert > 0) {
        System.out.println("The product is inserted.");
      } else {
        System.out.println("The product is not inserted. Please check with the administrator.");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        statement.close();
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  // fetch all the items from products table
  public static List<Product> selectProducts() {
    Connection connection = getConnection();
    Statement statement = null;
    List<Product> lstProduct = new ArrayList<>();
    try {
      statement = connection.createStatement();
      String sqlSelect ="select name, img, status, price from products";
      ResultSet rs = statement.executeQuery(sqlSelect);
      while (rs.next()) {
        String nameProduct = rs.getString("name");
        String imgProduct = rs.getString("img");
        String statusProduct = rs.getString("status");
        int priceProduct = rs.getInt("price");
        Product product = new Product(nameProduct, imgProduct, statusProduct, priceProduct);
        System.out.println(product.toString());
        lstProduct.add(product);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        statement.close();
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return lstProduct;
  }
}
