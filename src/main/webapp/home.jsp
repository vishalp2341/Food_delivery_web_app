<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.food.model.Restaurant, com.food.model.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<link rel="stylesheet" type="text/css" href="style.css">
<link rel="icon" href="img/delivery.png" type="image/png">
</head>
<body>
    <% String name = (String) session.getAttribute("name"); %>
    
    <header>
        <div class="header-container">
            <h1>Food Delivery</h1>
            <nav class="navbar">
                <ul>
                    <% if (name != null) { %>
                        <li><a href="cart.jsp" class="nav-link">Cart</a></li>
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
        <h2>Welcome <%= name != null ? name : "Guest" %>! What would you like to order?</h2>
    </div>
    
    <div class="restaurant-container">
        <%
        List<Restaurant> list = (List<Restaurant>) request.getAttribute("list");

        if (list != null) {
            for (Restaurant rest : list) {
        %>

        <div class="restaurant-column">
            <h2><%= rest.getName() %></h2>
            <div class="restaurant-image">
                <img src="<%= request.getContextPath() + "/" + rest.getImagePath() %>" alt="<%= rest.getName() %> Image">
            </div>
            <div class="restaurant-details">
                <p class="description">
                    <%= rest.getDescription() %>
                </p>
                <% if (rest.getIsActive() != null && rest.getIsActive()) { %>
                    <p class="status">
                        <img src="<%= request.getContextPath() %>/img/icons/open.png" class="icon" alt="Status Icon"> Open
                    </p>
                <% } else { %>
                    <p class="status closed">
                        <img src="<%= request.getContextPath() %>/img/icons/closed.png" class="icon" alt="Status Icon"> Closed
                    </p>
                <% } %>

                <p>
                    <img src="<%= request.getContextPath() %>/img/icons/address.png" class="icon" alt="Address Icon">
                    <%= rest.getAddress() %>
                </p>

                <p>
                    <img src="<%= request.getContextPath() %>/img/icons/phone.png" class="icon" alt="Phone Icon">
                    <%= rest.getPhone() %>
                </p>

                <p class="rating">
                    <% 
                    int rating = rest.getRating(); // Get the rating value
                    int maxRating = 5; // Maximum rating value
                    String filledStarPath = request.getContextPath() + "/img/icons/star-filled.png";
                    String emptyStarPath = request.getContextPath() + "/img/icons/star-empty.png";
                    %>

                    <% for (int i = 1; i <= maxRating; i++) { %>
                        <img src="<%= (i <= rating) ? filledStarPath : emptyStarPath %>" class="star" alt="Star Icon">
                    <% } %>
                </p>

                <p>
                    <img src="<%= request.getContextPath() %>/img/icons/cuisine.png" class="icon" alt="Cuisine Icon">
                    <%= rest.getCuisineType() %>
                </p>

                <p>
                    <img src="<%= request.getContextPath() %>/img/icons/eta.png" class="icon" alt="ETA Icon">
                    <%= rest.getEta() %> minutes
                </p>

                <!-- View Menu Button -->
                <a href="MenuServlet?restaurantId=<%= rest.getRestaurantId() %>" class="view-menu-button">View Menu</a>

            </div>
        </div>
        <%
        }
        } else {
        %>
        <p>No restaurants found.</p>
        <%
        }
        %>
    </div>
</body>
</html>
