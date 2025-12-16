package com.food.controller;

import com.food.daoimpl.OrderDaoImpl;
import com.food.daoimpl.OrderHistoryDaoImpl;
import com.food.daoimpl.OrderItemDaoImpl;
import com.food.model.Order;
import com.food.model.OrderHistory;
import com.food.model.OrderItem;
import com.food.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve User object from session
        User user = (User) session.getAttribute("user");
        Integer restaurantId = (Integer) session.getAttribute("restaurantId");
        Map<Integer, Integer> cartItems = (Map<Integer, Integer>) session.getAttribute("cart");
        Integer totalPrice = (Integer) session.getAttribute("totalPrice");

        // Handle form submission (order placement)
        String action = request.getParameter("action");
        int userId = (user != null) ? user.getUserId() : 0;

        OrderDaoImpl orderDao = new OrderDaoImpl();
        OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();
        OrderHistoryDaoImpl orderHistoryDao = new OrderHistoryDaoImpl(); // Instantiate OrderHistoryDaoImpl

        // Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setRestaurantId(restaurantId);
        order.setOrderDate(new Date());
        order.setTotalAmount(totalPrice);
        order.setStatus("Pending");
        order.setPaymentMode("Online"); // Example payment mode

        // Save Order and get the generated orderId
        int rowsAffected = orderDao.save(order);
        if (rowsAffected > 0) {
            int orderId = orderDao.getMaxOrderId();

            // Create Order Items
            for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
                Integer menuId = entry.getKey();
                Integer quantity = entry.getValue();
                
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                orderItem.setMenuId(menuId);
                orderItem.setQuantity(quantity);

                // Assuming totalPrice is for the entire order; individual item price might be different
                orderItem.setTotalPrice(totalPrice);

                int result = orderItemDao.save(orderItem);
                if (result <= 0) {
                    response.sendRedirect("error.jsp");
                    return;
                }
            }

            // Create Order History
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setUserId(userId);
            orderHistory.setOrderId(orderId);
            orderHistory.setOrderDate(new Date());
            orderHistory.setTotalAmount(totalPrice);
            orderHistory.setStatus("Completed");

            int historyRowsAffected = orderHistoryDao.save(orderHistory); // Correct method call

            if (historyRowsAffected > 0) {
                // Clear cart
//                session.removeAttribute("cart");

                // Redirect to checkout confirmation page
            	response.sendRedirect("checkout.jsp");
            	System.out.println("Redirecting to checkout.jsp.");
            	
            } else {
                response.sendRedirect("error.jsp");
                System.out.println("Error saving Order history");
            }
        } else {
            response.sendRedirect("error.jsp");
            System.out.println("Error saving Order.");
        }
    }
}
