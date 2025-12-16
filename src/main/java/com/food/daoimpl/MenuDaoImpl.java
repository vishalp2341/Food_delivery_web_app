package com.food.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.dao.MenuDao;
import com.food.model.Menu;

public class MenuDaoImpl implements MenuDao {

	private Connection connection;

	public MenuDaoImpl() {
		String url = "jdbc:mysql://127.0.0.1:3306/user";
		String username = "root";
		String password = "root";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int save(Menu menu) {
		String sql = "INSERT INTO menu (item_name, description, price, ratings, isAvailable, image_path) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, menu.getRestaurantId());
			stmt.setString(2, menu.getItemName());
			stmt.setString(3, menu.getDescription());
			stmt.setInt(4, menu.getPrice());
			stmt.setObject(5, menu.getRatings());
			stmt.setBoolean(6, menu.isAvailable());
			stmt.setString(7, menu.getImagePath());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int update(Menu menu) {
		String sql = "UPDATE menu SET restaurant_id = ?, item_name = ?, description = ?, price = ?, ratings = ?, isAvailable = ?, image_path = ? WHERE menu_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, menu.getRestaurantId());
			stmt.setString(2, menu.getItemName());
			stmt.setString(3, menu.getDescription());
			stmt.setInt(4, menu.getPrice());
			stmt.setObject(5, menu.getRatings());
			stmt.setBoolean(6, menu.isAvailable());
			stmt.setString(7, menu.getImagePath());
			stmt.setInt(8, menu.getMenuId());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(int menuId) {
		String sql = "DELETE FROM menu WHERE menu_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, menuId);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Menu get(int menuId) {
		String sql = "SELECT * FROM menu WHERE menu_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, menuId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Menu menu = new Menu();
					menu.setMenuId(rs.getInt("menu_id"));
					menu.setRestaurantId(rs.getInt("restaurant_id"));
					menu.setItemName(rs.getString("item_name"));
					menu.setDescription(rs.getString("description"));
					menu.setPrice(rs.getInt("price"));
					// Fetch ratings as a string
					String ratingsString = rs.getString("ratings");
					// Optionally, convert to integer if needed
					int ratings = (ratingsString != null && !ratingsString.isEmpty()) ? Integer.parseInt(ratingsString) : 0;
					menu.setRatings(ratings);
					menu.setAvailable(rs.getBoolean("isAvailable"));
					menu.setImagePath(rs.getString("image_path"));
					return menu;


				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Menu> getAll() {
		List<Menu> menus = new ArrayList<>();
		String sql = "SELECT * FROM menu";
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Menu menu = new Menu();
				menu.setMenuId(rs.getInt("menu_id"));
				menu.setRestaurantId(rs.getInt("restaurant_id"));
				menu.setItemName(rs.getString("item_name"));
				menu.setDescription(rs.getString("description"));
				menu.setPrice(rs.getInt("price"));
				menu.setRatings((Integer) rs.getObject("ratings"));
				menu.setAvailable(rs.getBoolean("isAvailable"));
				menu.setImagePath(rs.getString("image_path"));
				menus.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menus;
	}

	@Override
	public List<Menu> getMenuByRestaurantId(int restaurantId) {
		List<Menu> menus = new ArrayList<>();
		String sql = "SELECT * FROM menu WHERE restaurant_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, restaurantId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Menu menu = new Menu();
					menu.setMenuId(rs.getInt("menu_id"));
					menu.setRestaurantId(rs.getInt("restaurant_id"));
					menu.setItemName(rs.getString("item_name"));
					menu.setDescription(rs.getString("description"));
					menu.setPrice(rs.getInt("price"));
					menu.setRatings(rs.getInt("ratings"));
					menu.setAvailable(rs.getBoolean("isAvailable"));
					menu.setImagePath(rs.getString("image_path"));
					menus.add(menu);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menus;
	}
}
