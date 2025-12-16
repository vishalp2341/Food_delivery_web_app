package com.food.dao;

import java.util.List;

import com.food.model.User;

public interface UserDao {
	int save (User user);
	int update(User user);
	int delete(String email);
	User get(String email);
	int updateLastLoginDate(String email, java.sql.Timestamp lastLoginDate);
	List<User> getAll();

}
