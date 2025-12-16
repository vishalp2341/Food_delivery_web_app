<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.food.model.Menu"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Restaurant Menu</title>
<link rel="stylesheet" type="text/css" href="style.css">
<link rel="icon" href="img/delivery.png" type="image/png">
<style>
	.back {
    display: flex;
    justify-content: flex-start;
    margin: 20px 0;
    padding: 0;
    margin-left: -70%;
}

.go-back-button {
    background-color: #007bff;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
    text-decoration: none;
    display: inline-block;
    text-align: center;
}

.go-back-button:hover {
	transform: translateY(-5px);
    background-color: #0056b3;
}

.quantity-container {
    display: flex;
    align-items: center;
}
.quantity-container button {
    padding: 5px 10px;
    font-size: 16px;
}
.quantity-container input {
    width: 50px;
    text-align: center;
    font-size: 16px;
    margin: 0 5px;
}
</style>
<script>
    function adjustQuantity(menuId, action) {
        var quantityInput = document.getElementById("quantity_" + menuId);
        var currentQuantity = parseInt(quantityInput.value);
        if (action === 'increase') {
            quantityInput.value = currentQuantity + 1;
        } else if (action === 'decrease' && currentQuantity > 1) {
            quantityInput.value = currentQuantity - 1;
        }
    }
</script>
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

    <div class="welcome">
        <h1>Menu Items</h1>
    </div>

    <% String url = "HomeServlet"; %>
    <div class="back">
        <a href="<%=url%>" class="go-back-button">Go Back</a>
    </div>

    <div class="menu-container">
        <% List<Menu> menuItems = (List<Menu>) request.getAttribute("menuItems");

        if (menuItems != null && !menuItems.isEmpty()) {
            for (Menu item : menuItems) {
                int menuId = item.getMenuId();
                Integer quantity = (Integer) request.getAttribute("quantity_" + menuId);
                if (quantity == null) {
                    quantity = 1; // Default quantity if not set
                }
        %>
        <div class="menu-column">
            <div class="menu-image">
                <img src="<%= request.getContextPath() + "/" + item.getImagePath() %>" alt="<%= item.getItemName() %> Image">
            </div>
            <div class="menu-details">
                <h4><%= item.getItemName() %></h4>
                <p><%= item.getDescription() %></p>
                <p class="price">₹<%= item.getPrice() %></p>
                <p class="rating">
                    <% 
                    int rating = item.getRatings() != null ? item.getRatings() : 0;
                    int maxRating = 5;
                    String filledStarPath = request.getContextPath() + "/img/icons/star-filled.png";
                    String emptyStarPath = request.getContextPath() + "/img/icons/star-empty.png";
                    %>
                    <% for (int i = 1; i <= maxRating; i++) { %>
                    <img src="<%= (i <= rating) ? filledStarPath : emptyStarPath %>" class="star" alt="Star Icon">
                    <% } %>
                </p>
                <p class="status <%= item.isAvailable() ? "available" : "unavailable" %>">
                    <%= item.isAvailable() ? "Available" : "Unavailable" %>
                </p>

                <form action="cart" method="post">
                    <input type="hidden" name="menuId" value="<%= menuId %>">
                    <div class="quantity-container">
                        <button type="button" onclick="adjustQuantity(<%= menuId %>, 'decrease')">-</button>
                        <input type="text" id="quantity_<%= menuId %>" name="quantity" value="<%= quantity %>" readonly>
                        <button type="button" onclick="adjustQuantity(<%= menuId %>, 'increase')">+</button>
                    </div>
                    <button type="submit" name="action" value="addToCart" class="add-to-cart-button">Add to Cart</button>
                </form>

            </div>
        </div>
        <% 
            }
        } else { %>
        <p>No menu items available for this restaurant.</p>
        <% } %>
    </div>
</body>
</html>
