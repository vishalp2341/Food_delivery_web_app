package com.food.controller;

import com.food.dao.MenuDao;
import com.food.daoimpl.MenuDaoImpl;
import com.food.model.Menu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends HttpServlet {

    private MenuDao menuDao = new MenuDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve cart items from session
        Map<Integer, Integer> cartItems = (Map<Integer, Integer>) request.getSession().getAttribute("cart");
        if (cartItems == null) {
            cartItems = new HashMap<>();
        }
        System.out.println("Cart items retrieved from session: " + cartItems);

        // Retrieve form parameters
        String action = request.getParameter("action");
        Integer menuId = null;
        Integer quantity = 1; // Default value

        try {
            // Retrieve menuId from request or session
            if (request.getParameter("menuId") != null) {
                menuId = Integer.parseInt(request.getParameter("menuId"));
            } else {
                // Retrieve menuId from session if not provided in request
                menuId = (Integer) request.getSession().getAttribute("lastMenuId");
            }

            if (menuId == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Menu ID is required");
                return;
            }

            if ("addToCart".equals(action) || "update".equals(action)) {
                // Validate and retrieve quantity
                if (request.getParameter("quantity") != null) {
                    try {
                        quantity = Integer.parseInt(request.getParameter("quantity"));
                        if (quantity < 1) {
                            quantity = 1; // Ensure quantity is at least 1
                        }
                    } catch (NumberFormatException e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quantity format");
                        return;
                    }
                }

                if ("addToCart".equals(action)) {
                    cartItems.put(menuId, cartItems.getOrDefault(menuId, 0) + quantity);
                    System.out.println("Added to cart: Menu ID " + menuId + " with quantity " + quantity);
                } else if ("update".equals(action)) {
                    if (quantity > 0) {
                        cartItems.put(menuId, quantity);
                        System.out.println("Updated cart: Menu ID " + menuId + " with quantity " + quantity);
                    } else {
                        cartItems.remove(menuId);
                        System.out.println("Removed item with Menu ID: " + menuId + " due to quantity being zero or negative");
                    }
                }
            } else if ("delete".equals(action)) {
                cartItems.remove(menuId);
                System.out.println("Removed item with Menu ID: " + menuId);
            }

            // Save updated cart to session
            request.getSession().setAttribute("cart", cartItems);
            System.out.println("Updated cart items in session: " + cartItems);

            // Fetch menu details from the database
            Map<Integer, Menu> menuMap = new HashMap<>();
            for (Integer id : cartItems.keySet()) {
                Menu menu = menuDao.get(id);
                if (menu != null) {
                    menuMap.put(id, menu);
                    System.out.println("Fetched menu: " + menu + " for Menu ID: " + id);
                } else {
                    System.out.println("Menu with ID " + id + " not found in the database.");
                }
            }

            // Calculate total price
            int totalPrice = 0;
            for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
                Integer id = entry.getKey();
                Integer qty = entry.getValue();
                Menu menu = menuMap.get(id);
                if (menu != null) {
                    totalPrice += menu.getPrice() * qty;
                    System.out.println("Item: " + menu.getItemName() + ", Price: ₹" + menu.getPrice() + ", Quantity: " + qty + ", Partial Total: ₹" + (menu.getPrice() * qty));
                } else {
                    System.out.println("Menu with ID " + id + " not found in the menuMap.");
                }
            }
            System.out.println("Total Price Calculated: ₹" + totalPrice);

            // Save total price to session
            HttpSession session = request.getSession();
            session.setAttribute("totalPrice", totalPrice);

            // Retrieve restaurantId from session
            Integer restaurantId = (Integer) request.getSession().getAttribute("restaurantId");
            if (restaurantId == null) {
                restaurantId = 0; // Handle appropriately based on your application's logic
            }
            System.out.println("Restaurant ID retrieved from session: " + restaurantId);

            // Set attributes for the JSP
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("menuMap", menuMap);
            request.setAttribute("restaurantId", restaurantId);
            
            // Update lastMenuId in session
            if (menuId != null) {
                session.setAttribute("lastMenuId", menuId);
            }

            // Forward to cart.jsp
            System.out.println("Forwarding to cart.jsp with attributes: totalPrice=" + totalPrice + ", cartItems=" + cartItems + ", menuMap=" + menuMap + ", restaurantId=" + restaurantId);
            request.getRequestDispatcher("cart.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid menuId format");
        }
    }
}
