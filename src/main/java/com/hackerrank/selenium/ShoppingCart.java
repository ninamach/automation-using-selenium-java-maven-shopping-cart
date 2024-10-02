package com.hackerrank.selenium;

import com.hackerrank.selenium.db.Database;
import com.hackerrank.selenium.db.Product;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShoppingCart {

    public static void addProductsToCart(
            String productsURL, List<String> products, WebDriver driver) {
        driver.get(productsURL);
        List<WebElement> listForms = driver.findElements(By.xpath("//div[@class='thumbnail']"));
        for (WebElement from : listForms) {
            try {
                WebElement product = from.findElement(By.xpath(".//input[@name='name']"));
                String strProdCom = product.getAttribute("value");
                System.out.println("Product: " + strProdCom);
                for (String strProdGet : products) {
                    if (strProdCom.equals(strProdGet)) {
                        WebElement image = from.findElement(By.xpath(".//input[@name='img']"));
                        String imageValue = image.getAttribute("value");
                        WebElement price = from.findElement(By.xpath(".//input[@name='price']"));
                        String priceValue = price.getAttribute("value");
                        int intValue;
                        try {
                            intValue = Integer.parseInt(priceValue);
                        } catch(NumberFormatException nx) {
                            intValue = 0;
                        }
                        Product prod = new Product(strProdCom, imageValue, "carted", intValue);
                        Database.insertProduct(prod);
                    }
                }
            } catch (Exception ex) {
                System.out.println("Not found product3: " + ex.toString());
            }
        }
    }

    public static void checkout(String productsURL, WebDriver driver) {
        driver.get(productsURL);
        Database.deleteProducts();
    }
}
