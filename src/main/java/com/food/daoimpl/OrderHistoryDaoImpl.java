package com.food.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.dao.OrderHistoryDao;
import com.food.model.OrderHistory;

public class OrderHistoryDaoImpl implements OrderHistoryDao {
    private Connection connection;

    public OrderHistoryDaoImpl() {
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
    public int save(OrderHistory orderHistory) {
        String sql = "INSERT INTO order_history (user_id, order_id, orderDate, totalAmount, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, orderHistory.getUserId());
            stmt.setObject(2, orderHistory.getOrderId());
            stmt.setDate(3, new java.sql.Date(orderHistory.getOrderDate().getTime()));
            stmt.setObject(4, orderHistory.getTotalAmount());
            stmt.setObject(5, orderHistory.getStatus());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(OrderHistory orderHistory) {
        String sql = "UPDATE order_history SET user_id = ?, order_id = ?, orderDate = ?, totalAmount = ?, status = ? WHERE order_history_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, orderHistory.getUserId());
            stmt.setObject(2, orderHistory.getOrderId());
            stmt.setDate(3, new java.sql.Date(orderHistory.getOrderDate().getTime()));
            stmt.setObject(4, orderHistory.getTotalAmount());
            stmt.setObject(5, orderHistory.getStatus());
            stmt.setInt(6, orderHistory.getOrderHistoryId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(int orderHistoryId) {
        String sql = "DELETE FROM order_history WHERE order_history_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderHistoryId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public OrderHistory get(int orderHistoryId) {
        String sql = "SELECT * FROM order_history WHERE order_history_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderHistoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrderHistory orderHistory = new OrderHistory();
                    orderHistory.setOrderHistoryId(rs.getInt("order_history_id"));
                    orderHistory.setUserId((Integer) rs.getObject("user_id"));
                    orderHistory.setOrderId((Integer) rs.getObject("order_id"));
                    orderHistory.setOrderDate(rs.getDate("orderDate"));
                    orderHistory.setTotalAmount((Integer) rs.getObject("totalAmount"));
                    orderHistory.setStatus((String) rs.getObject("status"));
                    return orderHistory;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderHistory> getAll() {
        List<OrderHistory> orderHistories = new ArrayList<>();
        String sql = "SELECT * FROM order_history";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setOrderHistoryId(rs.getInt("order_history_id"));
                orderHistory.setUserId((Integer) rs.getObject("user_id"));
                orderHistory.setOrderId((Integer) rs.getObject("order_id"));
                orderHistory.setOrderDate(rs.getDate("orderDate"));
                orderHistory.setTotalAmount((Integer) rs.getObject("totalAmount"));
                orderHistory.setStatus((String) rs.getObject("status"));
                orderHistories.add(orderHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderHistories;
    }
}
