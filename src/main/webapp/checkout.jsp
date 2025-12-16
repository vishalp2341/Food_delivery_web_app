<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout</title>
    <link rel="stylesheet" href="checkout.css">
    <link rel="stylesheet" href="style.css">
    <link rel="icon" href="img/delivery.png" type="image/png">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .checkout-container {
            max-width: 500px;
            margin: 2rem auto;
            padding: 2rem;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            border: 1px solid #e0e0e0;
        }

        h1 {
            font-size: 2rem;
            color: #333;
            text-align: center;
            margin-bottom: 1.5rem;
            font-weight: bold;
        }

        .total-price-container {
            text-align: center;
            margin-bottom: 2rem;
            font-size: 1.5rem;
            color: #28a745;
        }

        .form-group {
            margin-bottom: 1.5rem;
            padding: 0 1rem;
        }

        .form-group label {
            display: block;
            font-size: 1rem;
            margin-bottom: 0.5rem;
            color: #555;
        }

        .form-group input, .form-group select {
            width: 100%;
            padding: 0.75rem;
            font-size: 1rem;
            border: 1px solid #ddd;
            border-radius: 6px;
            box-sizing: border-box;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        .form-group input:focus, .form-group select:focus {
            border-color: #007bff;
            box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
            outline: none;
        }

        .confirm-button {
            display: block;
            width: 100%;
            padding: 0.75rem;
            font-size: 1.1rem;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .confirm-button:hover {
            background-color: #0056b3;
        }

        .confirm-button:active {
            transform: scale(0.98);
        }

        .form-container {
            background: #f8f8f8;
            padding: 1rem;
            border-radius: 8px;
            border: 1px solid #ddd;
        }

        .form-group h2 {
            font-size: 1.1rem;
            color: #333;
            margin-bottom: 0.5rem;
        }
    </style>
</head>
<body>
    <!-- Header Section -->
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

    <!-- Checkout Form -->
    <div class="checkout-container">
        <h1>Checkout</h1>

        <% 
            Integer totalPrice = (Integer) session.getAttribute("totalPrice");
        %>

        <div class="total-price-container">
            <h2>Total Price: Rs.<%= totalPrice %></h2>
        </div>

        <div class="form-container">
            <form action="confirm" method="get">
                <div class="form-group">
                    <label for="address"><h2>Address:</h2></label>
                    <input type="text" id="address" name="address" required>
                </div>
                <div class="form-group">
                    <label for="paymentMode"><h2>Payment Mode:</h2></label>
                    <select id="paymentMode" name="paymentMode" required>
                        <option value="Online">Online</option>
                        <option value="Cash">Cash</option>
                    </select>
                </div>
                <button type="submit" name="action" value="confirmOrder" class="confirm-button">Confirm Order</button>
            </form>
        </div>
    </div>
</body>
</html>
