package com.hackerrank.selenium.db;

public class Product {
  public String name;
  public String img;
  public String status;
  public Integer price;

  public Product(String name, String img, String status, Integer price) {
    this.name = name;
    this.img = img;
    this.status = status;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product{"
        + "name='"
        + name
        + '\''
        + ", img='"
        + img
        + '\''
        + ", status='"
        + status
        + '\''
        + ", price="
        + price
        + '}';
  }
}
