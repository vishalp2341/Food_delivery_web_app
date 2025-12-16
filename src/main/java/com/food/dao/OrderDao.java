package com.food.dao;

import java.util.List;
import com.food.model.Order;

public interface OrderDao {
    int save(Order order);
    int update(Order order);
    int delete(int orderId);
    Order get(int orderId);
    List<Order> getAll();
}
