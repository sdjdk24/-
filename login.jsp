<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <script>
        function showAlert(message) {
            alert(message);
        }
    </script>
    <style>
        #button {
            width: 212px;
        }
    </style>
</head>
<body>
    <h2>로그인</h2>
    <form action="LoginServlet" method="post">
        <label for="username">ID:&nbsp;&nbsp;</label>
        <input type="text" id="username" name="username" required><br>
        <label for="password">PW:</label>
        <input type="password" id="password" name="password" required><br>
        <input type="submit" value="로그인" id="button"> <br>
    </form>
    <form action="register.jsp">
        <input type="submit" value="회원가입" id="button"/>
    </form>
    <% if (request.getAttribute("loginError") != null) { %>
        <script>
            showAlert("<%= request.getAttribute("loginError") %>");
        </script>
    <% } %>
</body>
</html>