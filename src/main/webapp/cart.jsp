<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.food.model.Menu"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Map.Entry"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Food Cart</title>
<link rel="stylesheet" type="text/css" href="style.css">
<link rel="stylesheet" type="text/css" href="cart.css">
<link rel="icon" href="img/delivery.png" type="image/png">
<script>
function adjustQuantity(menuId, action) {
    var quantityInput = document.getElementById("quantity_" + menuId);
    var currentQuantity = parseInt(quantityInput.value, 10);

    if (action === 'increase') {
        quantityInput.value = currentQuantity + 1;
    } else if (action === 'decrease') {
        if (currentQuantity > 1) {
            quantityInput.value = currentQuantity - 1;
        }
    }
}
</script>
</head>
<body>
    <%
    String name = (String) session.getAttribute("name");
    %>
    <%
    Integer restaurantId = (Integer) request.getSession().getAttribute("restaurantId");
    %>

    <header>
        <div class="header-container">
            <h1>Food Delivery</h1>
            <nav class="navbar">
                <ul>
                    <%
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

    <div class="page-title">
        <h2>Shopping Cart</h2>
    </div>

    <%
    String url = "MenuServlet"; // URL pattern as defined in web.xml
    if (restaurantId != null) {
        url += "?restaurantId=" + restaurantId;
    }
    %>
    <a href="<%=url%>" class="go-back-button">Add more items</a>
    <div class="cart-container">
        <%
        Map<Integer, Integer> cartItems = (Map<Integer, Integer>) session.getAttribute("cart");
        Map<Integer, Menu> menuMap = (Map<Integer, Menu>) request.getAttribute("menuMap");
        Integer totalPriceObj = (Integer) request.getAttribute("totalPrice");
        double totalPrice = (totalPriceObj != null) ? totalPriceObj : 0.0;

        if (cartItems != null && !cartItems.isEmpty() && menuMap != null) {
            for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
                Integer menuId = entry.getKey();
                Integer quantity = entry.getValue();
                Menu menu = menuMap.get(menuId);
                if (menu != null) {
        %>

        <div class="cart-item">
            <div class="cart-item-image">
                <img src="<%=request.getContextPath() + "/" + menu.getImagePath()%>"
                    alt="<%=menu.getItemName()%>">
            </div>

            <div class="cart-item-details">
                <h4><%=menu.getItemName()%></h4>
                <p><%=menu.getDescription()%></p>
                <p class="price">₹<%=menu.getPrice()%></p>
                <p class="quantity">Quantity: <%=quantity%></p>
            </div>

            <form action="cart" method="post" class="quantity-form update-form">
                <input type="hidden" name="menuId" value="<%= menuId %>">
                <input type="hidden" name="action" value="update">
                <div class="quantity-controls">
                    <button type="button" class="decrease-button" onclick="adjustQuantity(<%= menuId %>, 'decrease')">-</button>
                    <input type="text" id="quantity_<%= menuId %>" name="quantity" value="<%= quantity %>">
                    <button type="button" class="increase-button" onclick="adjustQuantity(<%= menuId %>, 'increase')">+</button>
                </div>
                <button type="submit" class="update-button">Update</button>
            </form>

            <form action="cart" method="post" class="delete-form">
                <input type="hidden" name="menuId" value="<%=menuId%>"> 
                <input type="hidden" name="action" value="delete">
                <button type="submit" class="delete-button">Delete</button>
            </form>

        </div>
        <%
                } 
            }
        %>
        <div class="cart-summary">
            <h3>Total Price: ₹<%=totalPrice%></h3>
            <form action="checkout" method="get">
                <button type="submit" class="checkout-button">Proceed to Checkout</button>
            </form>
        </div>
        <%
        } else {
        %>
        <p>Your cart is empty.</p>
        <%
        }
        %>
    </div>
</body>
</html>
