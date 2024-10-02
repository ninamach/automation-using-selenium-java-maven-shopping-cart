package com.hackerrank.selenium.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {
  private Server server;
  private int port;

  public JettyServer(int port) {
    this.port = port;
  }

  public void start() {
    try {
      // 1. Creating the server on given port
      server = new Server(port);

      // 2. Creating the WebAppContext for the created content
      WebAppContext ctx = new WebAppContext();
      ctx.setResourceBase("website");
      ctx.setContextPath("/");

      // disable directory listing site wise
      ctx.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
      ctx.addServlet("com.hackerrank.selenium.server.ShoppingCartServlet", "/cart.html");
      ctx.addServlet("com.hackerrank.selenium.server.ShoppingCartServlet", "/addToCart.html");
      ctx.addServlet("com.hackerrank.selenium.server.ShoppingCartServlet", "/checkout.html");

      // 2. Setting our secured handler
      server.setHandler(ctx);

      // 3. Starting the Server
      server.start();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public void stop() {
    try {
      server.stop();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
