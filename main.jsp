<%@page import="dao.MemberDao2"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
    String username = (String) session.getAttribute("username");
    
    MemberDao2 memberDAO = new MemberDao2();
    MemberDao2.User user = memberDAO.getUserDetails(username);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인 페이지</title>
    <style>
        #one {
            width: 600px;
        }
        #one > h2:nth-child(1) {
            width: 700px;
        }
        #two {
            margin-top: 20px;
            margin-right: 860px;
            display: grid;
            grid-template-columns: auto auto;
            align-items: center;
            gap: 10px;
        }
        
        .fl { 
            float: left;
        }
        .fr {
            float: right;
        }
        .button {
            width: 380px;
            background-color: white;
            border: 0px;
        }
        #adImage {
            margin-top: 20px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
	    $(document).ready(function() {
	        $("#logoutButton").click(function() {
	            alert("로그아웃 되었습니다.");
	        });
	
	        $(".button").click(function() {
	            var courseName = $(this).data("course"); // data 속성에서 과정 이름을 가져옴
	            console.log("Selected course: " + courseName); // 디버깅을 위해 추가
	
	            $.ajax({
	                url: "PurchaseServlet",
	                type: "POST",
	                data: { course: courseName },
	                success: function(response) {
	                    console.log("Response received:", response); // 디버깅을 위해 추가
	                    if (response.success) {
	                        alert("컨텐츠(" + courseName + ")를 구입하였습니다.");
	                    } else {
	                        alert(response.message);
	                    }
	                },
	                error: function() {
	                    alert("서버에 문제가 발생했습니다.");
	                }
	            });
	        });
	
	        $("#adImage").click(function() {
	            $.ajax({
	                url: "AdPointsServlet",
	                type: "POST",
	                success: function(response) {
	                    if (response.success) {
	                        alert(response.message);
	                        window.location.href = "http://koreaisacademy.com";
	                    } else {
	                        alert("포인트 적립에 실패했습니다.");
	                    }
	                },
	                error: function() {
	                    alert("서버에 문제가 발생했습니다.");
	                }
	            });
	        });
	    });
    </script>
</head>
<body>
    <div id="one" class="fl">
        <h2>메인 페이지</h2>
    </div>
    <div id="two" class="fr">
        <% 
            if (user != null) {
        %>
        <span class="fr"><%= user.getName() %> (<%= user.getId() %>)님 안녕하세요.</span>
        <form id="logoutForm" action="login.jsp" style="width: 70px;" class="fr">
            <input type="submit" value="로그아웃" id="logoutButton">
        </form>
        <br>
        <span>포인트 : <%= user.getPoints() %>점</span>
        <% } else { %>
        <span>사용자 정보를 불러올 수 없습니다.</span>
        <% } %>
    </div>
    <br><br><br><br><br>
    <div class="fl">구입할 컨텐츠를 선택하세요</div>
    <br><br>
    <div class="fl" style="margin-left: 5px;">
	    <img src="img/Intro_350_408.png" alt="intro"/> <br>
	    <button class="button" data-course="intro"> 100,000포인트</button>
	</div>
	<div class="fl" style="margin-left: 5px;">
	    <img src="img/Java_350_408.png" alt="java"/> <br>
	    <button class="button" data-course="java"> 500,000포인트</button>
	</div>
	<div class="fl" style="margin-left: 5px;">
	    <img src="img/Cpp_350_408.png" alt="cpp"/> <br>
	    <button class="button" data-course="cpp"> 300,000포인트</button>
	</div>

    <br><br><br><br><br>
    <div id="adImage" class="fr" style="margin-right: 860px;">
        <img src="img/korea_it.png"/> 
    </div>
</body>
</html>
