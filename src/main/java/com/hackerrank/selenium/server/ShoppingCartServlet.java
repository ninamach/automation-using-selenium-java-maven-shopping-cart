package com.hackerrank.selenium.server;

import com.hackerrank.selenium.db.Database;
import com.hackerrank.selenium.db.Product;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ShoppingCartServlet extends HttpServlet {
  private static final String ROW =
      "<tr>\n"
          + "\t\t\t\t\t<td><img width=\"100\" src=\"assets/img/$img\" alt=\"\"></td>\n"
          + "\t\t\t\t\t<td>$name</td>\n"
          + "\t\t\t\t\t<td>$price</td>\n"
          + "\t\t\t\t</tr>";

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setHeader("Cache-Control", "private, no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setCharacterEncoding("UTF-8");

    if ("/addToCart.html".equals(request.getRequestURI())) {
      System.out.println("Server->Processing URL: " + request.getRequestURL());

      String name = request.getParameter("name");
      String img = request.getParameter("img");
      Integer price = Integer.parseInt(request.getParameter("price"));

      Database.insertProduct(new Product(name, img, "carted", price));

      response.sendRedirect("/index.html");
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setHeader("Cache-Control", "private, no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setCharacterEncoding("UTF-8");

    if ("/cart.html".equals(request.getRequestURI())) {
      System.out.println("Server->Processing URL: " + request.getRequestURL());

      StringBuilder rows = new StringBuilder();
      List<Product> products = Database.selectProducts();
      for (Product product : products) {
        rows.append(
            ROW.replace("$name", product.name)
                .replace("$img", product.img)
                .replace("$price", "$" + product.price + ".00\n"));
      }
      response
          .getWriter()
          .write(
              String.join(
                      "\n",
                      Files.readAllLines(java.nio.file.Paths.get("website/cart_template.html")))
                  .replace("$ROWS", rows.toString()));
    } else if ("/checkout.html".equals(request.getRequestURI())) {
      List<Product> products = Database.selectProducts();
      Database.deleteProducts();
      if (!products.isEmpty()) {
        response.getWriter().write("<h1>Checked out " + products.size() + " products!</h1>");
      } else {
        response.getWriter().write("Empty cart!");
      }
    }
  }
}
