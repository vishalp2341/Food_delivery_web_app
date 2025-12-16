package com.food.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ClearSessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Clear session attributes
        session.removeAttribute("cart");
        session.removeAttribute("totalPrice");
        session.removeAttribute("restaurantId");
        
        
        System.out.println("session cleared in clear session servlet");
        // Redirect to homepage or any other page
        response.sendRedirect("HomeServlet");
    }
}
