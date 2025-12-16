<%@ page import="java.util.List" %>
<%@ page import="com.food.model.OrderHistory" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order History</title>
    
    <link rel="stylesheet" href="style.css">
    <link rel="icon" href="img/delivery.png" type="image/png">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #333;
            color: #fff;
        }
        tr:hover {
            background-color: #f2f2f2;
        }
        h1 {
            color: #333;
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<% String name = (String) session.getAttribute("name"); %>

    <header>
        <div class="header-container">
            <h1>Food Delivery</h1>
            <nav class="navbar">
                <ul>
                    <% if (name != null) { %>
                    <li><form id="cartForm" action="cart" method="post" style="display: none;">
    <input type="hidden" name="fromNavbar" value="true">
</form>
<a href="#" class="nav-link" onclick="document.getElementById('cartForm').submit(); return false;">Cart</a>
</li>
                    <li><a href="orderHistory" class="nav-link">Order History</a></li>
                    <li><a href="logout" class="nav-link">Logout</a></li>
                    <% } else { %>
                    <li><a href="Login.jsp" class="nav-link">Login</a></li>
                    <li><a href="Register.jsp" class="nav-link">Register</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>

    <div class="container">
        <h1>Order History</h1>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>User ID</th>
                    <th>Order Date</th>
                    <th>Total Amount</th> <!-- Added Total Amount Column -->
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Retrieve the list of order history from the request attribute
                    List<OrderHistory> orderHistoryList = (List<OrderHistory>) request.getAttribute("orderHistoryList");
                    if (orderHistoryList != null) {
                        for (OrderHistory orderHistory : orderHistoryList) {
                %>
                <tr>
                    <td><%= orderHistory.getOrderId() %></td>
                    <td><%= orderHistory.getUserId() %></td>
                    <td><%= orderHistory.getOrderDate() %></td>
                    <td><%= orderHistory.getTotalAmount() %></td> <!-- Display Total Amount -->
                    <td><%= orderHistory.getStatus() %></td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5">No orders found.</td> <!-- Adjusted colspan for the added column -->
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
