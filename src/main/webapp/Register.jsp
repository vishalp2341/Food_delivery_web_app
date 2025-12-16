<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Food Delivery Register Form</title>
  <link rel="stylesheet" href="style.css">
<link rel="icon" href="img\delivery.png" type="image/png">

  
</head>
<body>
  <div class="wrapper">
    <form action="register" method="POST">
      <h2>Register</h2>
      
      <div class="input-field">
        <input type="text" id="name" name="name" required>
        <label for="name">Enter your name</label>
      </div>

      <div class="input-field">
        <input type="text" id="username" name="username" required>
        <label for="username">Create a username</label>
      </div>

      <div class="input-field">
        <input type="email" id="email" name="email" required>
        <label for="email">Enter your email</label>
      </div>

      <div class="input-field">
        <input type="tel" id="phone" name="phone" required>
        <label for="phone">Enter your phone number</label>
      </div>

      <div class="input-field">
        <input type="password" id="password" name="password" required>
        <label for="password">Enter your password</label>
      </div>
		
		<% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
    <% } %>
      <button type="submit">Register</button>
		
      <div class="login">
        <p>Already have an account? <a href="Login.jsp">Login</a></p>
      </div>
    </form>
  </div>
</body>
</html>
