	package com.food.controller;
	
	import java.io.IOException; 
	import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	
	import com.food.daoimpl.RestaurantDaoImpl;
	import com.food.model.Restaurant;
	
	public class HomeServlet extends HttpServlet {
		
		@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			RestaurantDaoImpl restdaoimp = new RestaurantDaoImpl();
			
			List <Restaurant> list = restdaoimp.getAll();
			req.setAttribute("list", list);
			
			RequestDispatcher d = req.getRequestDispatcher("home.jsp");
			d.forward(req, resp);
		}
	
	}
