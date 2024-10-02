package com.hackerrank.selenium;

import com.hackerrank.selenium.db.Database;
import com.hackerrank.selenium.server.JettyServer;
import com.hackerrank.selenium.server.util.SeleniumUtil;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.Timer;

public class AppMain {
  // by default, it waits for 5 minutes, server shuts down after this. You can change it for
  // debugging.
  // Static URL: http://localhost:8000/static/vulnPage.html
  // Dynamic URL: http://localhost:8000/dynamic/vulnPage.html
  // Note: Tests will launch server at 8080 port
  public static final int TIME_OUT = 5 * 60 * 1000;
  public static final int PORT = 8000;

  public static void main(String[] args) {
    String productURL = "http://localhost:" + PORT;
    JettyServer server = new JettyServer(PORT);
    server.start();
    System.out.println("Server started! " + productURL);

    Database.init();
    System.out.println("Database initialized!");

    WebDriver driver = SeleniumUtil.createDriver();
    System.out.println("Driver initialized!");

    Timer timer = new java.util.Timer();
    timer.schedule(
            new java.util.TimerTask() {
              @Override
              public void run() {
                server.stop();
                System.exit(0);
              }
            },
            TIME_OUT);

    // addToCart
    ShoppingCart.addProductsToCart(
            productURL, Arrays.asList(new String[] {"Product3", "Product7"}), driver);
    System.out.println("Products added to cart: " + Database.selectProducts());

    // checkout
    ShoppingCart.checkout(productURL, driver);
    System.out.println(
            "Is cart empty after checkout? Cart size: " + Database.selectProducts().size());

    System.out.println(
            "\n Enter ctl+c to exit, otherwise it will auto exit after "
                    + TIME_OUT / (1000 * 60)
                    + " minutes!");
  }
}
