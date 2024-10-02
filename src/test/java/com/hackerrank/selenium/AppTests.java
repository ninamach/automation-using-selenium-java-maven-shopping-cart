package com.hackerrank.selenium;

import com.hackerrank.selenium.db.Database;
import com.hackerrank.selenium.db.Product;
import com.hackerrank.selenium.server.JettyServer;
import com.hackerrank.selenium.server.util.SeleniumUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTests {
  private static JettyServer server = null;
  private static WebDriver driver = null;
  private static List<String> products;
  private static Random random;
  public static final int TEST_PORT = 8080;
  private static final String productURL = "http://localhost:" + TEST_PORT;

  @BeforeAll
  public static void setup() {
    driver = SeleniumUtil.createDriver();

    server = new JettyServer(TEST_PORT);
    server.start();

    Database.init();

    random = new Random();
    products =
        new ArrayList<>(
            Arrays.asList(
                "Product1",
                "Product2",
                "Product3",
                "Product4",
                "Product5",
                "Product6",
                "Product7",
                "Product8",
                "Product9",
                "Product10",
                "Product11",
                "Product12"));
  }

  @AfterAll
  public static void tearDown() {
    driver.close();
    server.stop();
  }

  @Test
  @Order(1)
  public void testAddToCart() {
    List<String> expected = products.subList(0, random.nextInt(10) + 2);
    ShoppingCart.addProductsToCart(productURL, expected, driver);

    List<Product> actual = Database.selectProducts();

    System.out.println(" Actual: \n" + actual + "\n Expected: \n" + expected);

    Assertions.assertEquals(expected.size(), actual.size());
    for (Product product : actual) {
      Assertions.assertTrue(expected.contains(product.name));
      Assertions.assertEquals("carted", product.status);
    }
  }

  @Test
  @Order(2)
  public void testCheckout() {
    List<String> expected =
        Database.selectProducts().stream().map(p -> p.name).collect(Collectors.toList());
    Assertions.assertTrue(expected.size() > 0);

    ShoppingCart.checkout(productURL, driver);

    List<Product> actual = Database.selectProducts();

    System.out.println(" Actual: \n" + actual + "\n Expected: []\n");

    Assertions.assertEquals(0, actual.size());
  }
}
