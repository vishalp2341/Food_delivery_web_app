package com.food.dao;

import java.util.List;
import com.food.model.OrderItem;

public interface OrderItemDao {
    int save(OrderItem orderItem);
    int update(OrderItem orderItem);
    int delete(int orderItemId);
    OrderItem get(int orderItemId);
    List<OrderItem> getAll();
}
