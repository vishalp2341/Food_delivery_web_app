package com.food.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.dao.OrderHistoryDao;
import com.food.daoimpl.OrderHistoryDaoImpl;
import com.food.model.OrderHistory;


public class OrderHistoryServlet extends HttpServlet {

    private OrderHistoryDao orderHistoryDao = new OrderHistoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<OrderHistory> orderHistoryList = orderHistoryDao.getAll();
        request.setAttribute("orderHistoryList", orderHistoryList);
        request.getRequestDispatcher("/orderHistory.jsp").forward(request, response);

    }
}
