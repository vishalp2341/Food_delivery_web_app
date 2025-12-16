package com.food.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.food.model.Restaurant;
import com.food.dao.RestaurantDao;
import com.food.dao.MenuDao; // Import MenuDao
import com.food.daoimpl.MenuDaoImpl; // Import MenuDaoImpl
import com.food.daoimpl.RestaurantDaoImpl;

import java.util.List;
import com.food.model.Menu; // Import Menu model

public class MenuServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String restaurantIdParam = req.getParameter("restaurantId");

        if (restaurantIdParam != null && !restaurantIdParam.isEmpty())
        {
            try {
                int restaurantId = Integer.parseInt(restaurantIdParam);
                req.getSession().setAttribute("restaurantId", restaurantId);
                
                // Create DAO instances
                RestaurantDao restaurantDao = new RestaurantDaoImpl();
                MenuDao menuDao = new MenuDaoImpl(); // Use the implementation

                // Fetch the restaurant details based on restaurantId
                Restaurant restaurant = restaurantDao.get(restaurantId);
                
                List<Menu> menuItems = menuDao.getMenuByRestaurantId(restaurantId);

                if (restaurant != null) 
                {
                    req.setAttribute("restaurant", restaurant);
                    req.setAttribute("menuItems", menuItems); // Set menu items as request attribute
                    // Forward to the JSP page to display the restaurant menu
                    req.getRequestDispatcher("menu.jsp").forward(req, resp);
                } else
                {
                    // Handle the case where the restaurant is not found
                    req.setAttribute("errorMessage", "Restaurant not found");
                    req.getRequestDispatcher("Home.jsp").forward(req, resp);
                }
            } 
            
            catch (NumberFormatException e) 
            {
                // Handle the case where restaurantId is not a valid number
                req.setAttribute("errorMessage", "Invalid restaurant ID");
                req.getRequestDispatcher("Home.jsp").forward(req, resp);
            }
        } 
        
        else
        {
            // Handle the case where restaurantId is missing
            req.setAttribute("errorMessage", "Restaurant ID is missing");
            req.getRequestDispatcher("Home.jsp").forward(req, resp);
        }
    }
}
