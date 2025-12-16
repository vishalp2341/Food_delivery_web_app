package com.food.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.food.dao.UserDao;
import com.food.daoimpl.UserDaoImpl;
import com.food.model.User;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDao userDao;

    public LoginServlet() {
        super();
        userDao = new UserDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        User user = userDao.get(email);

        if (user != null && user.getPassword().equals(password)) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            userDao.updateLastLoginDate(email, now);

            HttpSession session = request.getSession();
            session.setAttribute("user", user); 
            session.setAttribute("name", user.getName()); 
            
            System.out.println("User stored in session: " + user);
            System.out.println("LoginServlet Session ID: " + session.getId());

            response.sendRedirect("HomeServlet");
        } else {
            request.setAttribute("errorMessage", "Invalid email or password");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
}
