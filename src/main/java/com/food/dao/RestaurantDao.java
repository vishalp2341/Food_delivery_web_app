package com.food.dao;

import java.util.List;
import com.food.model.Restaurant;

public interface RestaurantDao {
    int save(Restaurant restaurant);
    int update(Restaurant restaurant);
    int delete(int restaurantId); // Using int for restaurantId as it matches the Restaurant model
    Restaurant get(int restaurantId); // Using int for restaurantId as it matches the Restaurant model
    List<Restaurant> getAll();
}
