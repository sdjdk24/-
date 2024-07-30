<%@page import="dao.MemberDao2"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
</head>
<body>
    <h2>회원가입</h2>
    <form action="" method="post">
        <label for="username">ID :&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <input type="text" id="username" name="username" required><br>
        <label for="password">PW :&nbsp;&nbsp;</label>
        <input type="password" id="password" name="password" required><br>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br>
        <input type="submit" value="작성완료">
    </form>
    <% 
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        if (username != null && password != null && name != null) {
            MemberDao2 memberDAO = new MemberDao2();
            memberDAO.registerMember(username, password, name);

            out.println("<script>alert('가입되었습니다. 로그인 해주세요.'); window.location.href='login.jsp';</script>");
        }
    %>
</body>
</html>