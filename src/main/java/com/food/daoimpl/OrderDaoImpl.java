package com.food.daoimpl;

import com.food.dao.OrderDao;
import com.food.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private Connection connection;

    public OrderDaoImpl() {
        String url = "jdbc:mysql://127.0.0.1:3306/user";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int save(Order order) {
        String sql = "INSERT INTO `orders` (user_id, restaurant_id, orderDate, totalAmount, status, paymentMode) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUserId());
            stmt.setInt(2, order.getRestaurantId());
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setInt(4, order.getTotalAmount());
            stmt.setString(5, order.getStatus());
            stmt.setString(6, order.getPaymentMode());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated order_id
                }
            } else {
                System.err.println("No rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing save: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Order order) {
        String sql = "UPDATE `orders` SET user_id = ?, restaurant_id = ?, orderDate = ?, totalAmount = ?, status = ?, paymentMode = ? WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getUserId());
            stmt.setInt(2, order.getRestaurantId());
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setInt(4, order.getTotalAmount());
            stmt.setString(5, order.getStatus());
            stmt.setString(6, order.getPaymentMode());
            stmt.setInt(7, order.getOrderId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(int orderId) {
        String sql = "DELETE FROM `orders` WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing delete: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Order get(int orderId) {
        String sql = "SELECT * FROM `orders` WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setRestaurantId(rs.getInt("restaurant_id"));
                    order.setOrderDate(rs.getDate("orderDate"));
                    order.setTotalAmount(rs.getInt("totalAmount"));
                    order.setStatus(rs.getString("status"));
                    order.setPaymentMode(rs.getString("paymentMode"));
                    return order;
                } else {
                    System.err.println("No order found with id: " + orderId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing get: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
        
    }
    
    public int getMaxOrderId() {
        String sql = "SELECT MAX(order_id) AS max_id FROM orders";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Default value if no max_id is found
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `orders`";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setRestaurantId(rs.getInt("restaurant_id"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setTotalAmount(rs.getInt("totalAmount"));
                order.setStatus(rs.getString("status"));
                order.setPaymentMode(rs.getString("paymentMode"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Error executing getAll: " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }
}
