package com.food.dao;

import java.util.List;
import com.food.model.Menu;

public interface MenuDao {
    int save(Menu menu);
    int update(Menu menu);
    int delete(int menuId);
    Menu get(int menuId);
    List<Menu> getAll();
    List<Menu> getMenuByRestaurantId(int restaurantId);
}
