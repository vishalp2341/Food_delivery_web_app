package com.food.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.food.dao.OrderItemDao;
import com.food.model.OrderItem;

public class OrderItemDaoImpl implements OrderItemDao {
    private Connection connection;

    public OrderItemDaoImpl() {
        String url = "jdbc:mysql://127.0.0.1:3306/user";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int save(OrderItem orderItem) {
        String sql = "INSERT INTO order_item (order_id, menu_id, quantity, total_price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getMenuId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setInt(4, orderItem.getTotalPrice());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(OrderItem orderItem) {
        String sql = "UPDATE order_item SET order_id = ?, menu_id = ?, quantity = ?, total_price = ? WHERE order_item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getMenuId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setInt(4, orderItem.getTotalPrice());
            stmt.setInt(5, orderItem.getOrderItemId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(int orderItemId) {
        String sql = "DELETE FROM order_item WHERE order_item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderItemId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public OrderItem get(int orderItemId) {
        String sql = "SELECT * FROM order_item WHERE order_item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderItemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderItemId(rs.getInt("order_item_id"));
                    orderItem.setOrderId(rs.getInt("order_id"));
                    orderItem.setMenuId(rs.getInt("menu_id"));
                    orderItem.setQuantity(rs.getInt("quantity"));
                    orderItem.setTotalPrice(rs.getInt("total_price"));
                    return orderItem;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderItem> getAll() {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_item";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getInt("order_item_id"));
                orderItem.setOrderId(rs.getInt("order_id"));
                orderItem.setMenuId(rs.getInt("menu_id"));
                orderItem.setQuantity(rs.getInt("quantity"));
                orderItem.setTotalPrice(rs.getInt("total_price"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
    
    
    public int getMaxOrderId() {
        String sql = "SELECT MAX(order_id) AS max_order_id FROM orders";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("max_order_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 or handle appropriately if no max order ID is found
    }
}
