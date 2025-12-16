package com.food.dao;

import java.util.List;
import com.food.model.OrderHistory;

public interface OrderHistoryDao {
    int save(OrderHistory orderHistory);
    int update(OrderHistory orderHistory);
    int delete(int orderHistoryId);
    OrderHistory get(int orderHistoryId);
    List<OrderHistory> getAll();
}
