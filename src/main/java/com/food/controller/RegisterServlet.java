package com.food.controller;

import java.io.IOException; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.food.daoimpl.UserDaoImpl;
import com.food.model.User;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        long phone = Long.parseLong(req.getParameter("phone"));
        String password = req.getParameter("password");

        UserDaoImpl uimpl = new UserDaoImpl();
        
        User u = new User(name, username, password, email, phone);
        u.setRole("Customer");
        
        try {
            int result = uimpl.save(u);
            
            if (result == 1) {
                // Registration successful
                resp.sendRedirect("Login.jsp");
            } else {
                // Registration failed, set error message and forward to registration page
                req.setAttribute("errorMessage", "Registration failed. Please try again.");
                req.getRequestDispatcher("Register.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
            req.getRequestDispatcher("Register.jsp").forward(req, resp);
        }
    }
}
