<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.MemberDao2" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원관리-수정관리자</title>
    <script>
        function showAlert() {
            alert('수정되었습니다.');
        }
    </script>
</head>
<body>
    <h2>회원관리-수정관리자</h2>
    <form id="editForm" action="UpdateMemberServlet" method="post" onsubmit="showAlert()">
        <label for="id">ID </label>
        <input type="text" name="id" value="<%= request.getParameter("id") %>" readonly> <br/>
        <label for="password">Password:</label>
        <input type="text" id="password" name="password" value="<%= request.getParameter("password") %>">
        <br/>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="<%= request.getParameter("name") %>">
        <br/>
        <label for="points">Points:</label>
        <input type="number" id="points" name="points" value="<%= request.getParameter("points") %>">
        <br/>
        <button type="submit">제출</button>
    </form>
</body>
</html>
