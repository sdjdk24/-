<%@page import="dto.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.MemberDao2" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 페이지</title>
    <style>
        table { width: 40%; border-collapse: collapse; }
        table, th, td { border: 1px solid black; }
        th, td { padding: 10px; text-align: left; }
        button { margin: 5px; }
    </style>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function() {
            $('.deleteButton').click(function() {
                var id = $(this).data('id');
                if (confirm('정말로 삭제하시겠습니까?')) {
                    $.post('DeleteMemberServlet', { id: id }, function(response) {
                        if (response.success) {
                            alert('삭제되었습니다.');
                            location.reload();
                        } else {
                            alert('삭제 실패.');
                        }
                    }, 'json');
                }
            });
        });
    </script>
</head>
<body>
    <div style="display: flex; justify-content: space-between;">
        <h2>회원 관리</h2>
        <form action="login.jsp" style="margin-top: 28px; margin-right: 1200px">
            <input type="submit" value="로그인">
        </form>
    </div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>PW</th>
                <th>Name</th>
                <th>Points</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <% 
                MemberDao2 memberDAO = new MemberDao2();
                List<Member> members = memberDAO.getAllMembers(); // 모든 회원 정보를 가져오는 메소드 호출
                if (members != null && !members.isEmpty()) {
                    for (Member member : members) {
            %>
            <tr>
                <td><%= member.getId() %></td>
                <td><%= member.getPassword() %></td>
                <td><%= member.getName() %></td>
                <td><%= member.getPoints() %></td>
                <td><a href="editMember.jsp?id=<%= member.getId() %>&password=<%= member.getPassword() %>&name=<%= member.getName() %>&points=<%= member.getPoints() %>">수정</a></td>
                <td><button class="deleteButton" data-id="<%= member.getId() %>">삭제</button></td>
            </tr>
            <% 
                    }
                } else {
            %>
            <tr>
                <td colspan="6">회원 정보가 없습니다.</td>
            </tr>
            <% 
                }
            %>
        </tbody>
    </table>

    <h2>스케줄러 관리</h2>
    <form action="SchedulerServlet" method="post">
        <input type="submit" name="action" value="스케줄러(20초마다 포인트 1증가) 실행 시작">
        <input type="submit" name="action" value="스케줄러 실행 종료">
    </form>
</body>
</html>
