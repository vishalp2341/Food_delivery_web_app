<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Food Delivery Login</title>
  <link rel="stylesheet" href="style.css">
  <link rel="icon" href="img/delivery.png" type="image/png">
</head>
<body>
  <div class="wrapper">
    <form action="LoginServlet" method="POST">
      <h2>Login</h2>
      
      <div class="input-field">
        <input type="email" id="email" name="email" required>
        <label for="email">Enter your email</label>
      </div>

      <div class="input-field">
        <input type="password" id="password" name="password" required>
        <label for="password">Enter your password</label>
      </div>
      
      <% 
        // Display error message if it exists
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) { 
      %>
      <div class="error-message" style="margin-top: -20px;">
        <p style="color: red;"><%= errorMessage %></p>
      </div>
      <% 
        } 
      %>

      <button type="submit">Log In</button>

      <div class="register">
        <p>Don't have an account? <a href="Register.jsp">Register</a></p>
      </div>
    </form>
  </div>
</body>
</html>
