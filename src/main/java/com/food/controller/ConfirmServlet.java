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

public class ConfirmServlet extends HttpServlet {

    private MenuDao menuDao = new MenuDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<Integer, Integer> cartItems = (Map<Integer, Integer>) session.getAttribute("cart");
        Integer totalPrice = (Integer) session.getAttribute("totalPrice");

        // Check for null values and redirect if necessary
        if (cartItems == null || totalPrice == null) {
        	 System.out.println( "----------------------cartItems == null || totalPrice == null-------------------");
             System.out.println(cartItems);
             System.out.println(totalPrice);
        }

        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Integer id : cartItems.keySet()) {
            Menu menu = menuDao.get(id);
            if (menu != null) {
                menuMap.put(id, menu);
            }
        }

        // Set attributes for JSP
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("menuMap", menuMap);
        request.setAttribute("totalPrice", totalPrice);

        // Debugging output
        System.out.println("ConfirmServlet - Cart Items: " + cartItems);
        System.out.println("ConfirmServlet - Menu Map: " + menuMap);
        System.out.println("ConfirmServlet - Total Price: " + totalPrice);

        // Forward to confirm.jsp
        request.getRequestDispatcher("confirm.jsp").forward(request, response);
    }
}
