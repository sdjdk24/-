package servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao2;

@WebServlet("/AdPointsServlet")
public class AdPointsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");

        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        MemberDao2 memberDAO = new MemberDao2();
        Random random = new Random();
        int pointsEarned = random.nextInt(1000) + 1; // 1 이상 1000 이하의 난수 생성

        boolean success = memberDAO.addPoints(username, pointsEarned);

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("{\"success\": " + success + ", \"message\": \"" + pointsEarned + "점이 적립되었습니다.\"}");
    }
}