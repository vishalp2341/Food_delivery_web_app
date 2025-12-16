<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.food.model.Menu"%>
<%@ page import="java.util.Map.Entry"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Confirmation</title>
<link rel="stylesheet" type="text/css" href="confirm.css">
<link rel="stylesheet" type="text/css" href="style.css">
<link rel="stylesheet" type="text/css" href="cart.css">
<link rel="icon" href="img/delivery.png" type="image/png">
</head>
<body>
    <header>
        <div class="header-container">
            <h1>Food Delivery</h1>
            <nav class="navbar">
                <ul>
                    <%
                    String name = (String) session.getAttribute("name");
                    if (name != null) {
                    %>
                    <li><a href="cart" class="nav-link">Cart</a></li>
                    <li><a href="orderHistory" class="nav-link">Order History</a></li>
                    <li><a href="logout" class="nav-link">Logout</a></li>
                    <%
                    } else {
                    %>
                    <li><a href="Login.jsp" class="nav-link">Login</a></li>
                    <li><a href="Register.jsp" class="nav-link">Register</a></li>
                    <%
                    }
                    %>
                </ul>
            </nav>
        </div>
    </header>

    <div class="confirm-container">
        <div class="page-title">
            <h1>Thank You for Your Order!</h1>
        </div>
        <%
        Map<Integer, Integer> cartItems = (Map<Integer, Integer>) session.getAttribute("cart");
        Map<Integer, Menu> menuMap = (Map<Integer, Menu>) request.getAttribute("menuMap");
        Integer totalPrice = (Integer) session.getAttribute("totalPrice");

        if (cartItems != null && !cartItems.isEmpty() && menuMap != null) {
            for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
                Integer menuId = entry.getKey();
                Integer quantity = entry.getValue();
                Menu menu = menuMap.get(menuId);
                if (menu != null) {
        %>

        <div class="item">  
          <div class="cart-item-image">
                <img src="<%=request.getContextPath() + "/" + menu.getImagePath()%>"
                    alt="<%=menu.getItemName()%>" style="size:200px;">
            </div>

            <div class="cart-item-details">
                <h4><%=menu.getItemName()%></h4>
                <p class="price">₹<%=menu.getPrice()%></p>
                <p class="quantity">Quantity: <%=quantity%></p>
            </div>

        </div>

        <%
                } // End of menu null check
            }
        }
        %>
        <div class="total-price">
            <h2>Total Price: ₹<%= totalPrice %></h2>
        </div>
        <a href="clear" class="confirm-button">Go to Homepage</a>
    </div>
</body>
</html>
