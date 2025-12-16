package com.food.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.dao.RestaurantDao;
import com.food.model.Restaurant;

public class RestaurantDaoImpl implements RestaurantDao {

    private Connection connection;

    public RestaurantDaoImpl() {
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
    public int save(Restaurant restaurant) {
        String sql = "INSERT INTO restaurant (name, address, phone, rating, cuisine_type, isActive, eta, description, user_id, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getName());
            stmt.setString(2, restaurant.getAddress());
            stmt.setLong(3, restaurant.getPhone());
            stmt.setInt(4, restaurant.getRating()); // Use setInt for rating
            stmt.setString(5, restaurant.getCuisineType());
            stmt.setBoolean(6, restaurant.getIsActive()); // Use setBoolean for isActive
            stmt.setInt(7, restaurant.getEta()); // Use setInt for eta
            stmt.setString(8, restaurant.getDescription());
            stmt.setInt(9, restaurant.getUserId()); // Use setInt for user_id
            stmt.setString(10, restaurant.getImagePath());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Restaurant restaurant) {
        String sql = "UPDATE restaurant SET name = ?, address = ?, phone = ?, rating = ?, cuisine_type = ?, isActive = ?, eta = ?, description = ?, user_id = ?, image_path = ? WHERE restaurant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getName());
            stmt.setString(2, restaurant.getAddress());
            stmt.setLong(3, restaurant.getPhone());
            stmt.setInt(4, restaurant.getRating()); // Use setInt for rating
            stmt.setString(5, restaurant.getCuisineType());
            stmt.setBoolean(6, restaurant.getIsActive()); // Use setBoolean for isActive
            stmt.setInt(7, restaurant.getEta()); // Use setInt for eta
            stmt.setString(8, restaurant.getDescription());
            stmt.setInt(9, restaurant.getUserId()); // Use setInt for user_id
            stmt.setString(10, restaurant.getImagePath());
            stmt.setInt(11, restaurant.getRestaurantId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(int restaurantId) {
        String sql = "DELETE FROM restaurant WHERE restaurant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Restaurant get(int restaurantId) {
        String sql = "SELECT * FROM restaurant WHERE restaurant_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setRestaurantId(rs.getInt("restaurant_id"));
                    restaurant.setName(rs.getString("name"));
                    restaurant.setAddress(rs.getString("address"));
                    restaurant.setPhone(rs.getLong("phone"));
                    restaurant.setRating(rs.getInt("rating")); // Use getInt for rating
                    restaurant.setCuisineType(rs.getString("cuisine_type"));
                    restaurant.setIsActive(rs.getBoolean("isActive")); // Use getBoolean for isActive
                    restaurant.setEta(rs.getInt("eta")); // Use getInt for eta
                    restaurant.setDescription(rs.getString("description"));
                    restaurant.setUserId(rs.getInt("user_id")); // Use getInt for user_id
                    restaurant.setImagePath(rs.getString("image_path"));
                    return restaurant;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurant";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt("restaurant_id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setPhone(rs.getLong("phone"));
                restaurant.setRating(rs.getInt("rating")); // Use getInt for rating
                restaurant.setCuisineType(rs.getString("cuisine_type"));
                restaurant.setIsActive(rs.getBoolean("isActive")); // Use getBoolean for isActive
                restaurant.setEta(rs.getInt("eta")); // Use getInt for eta
                restaurant.setDescription(rs.getString("description"));
                restaurant.setUserId(rs.getInt("user_id")); // Use getInt for user_id
                restaurant.setImagePath(rs.getString("image_path"));
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}
