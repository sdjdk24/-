package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao2;
import dto.Member;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	MemberDao2 memberDao = new MemberDao2();
        List<Member> members = memberDao.getAllMembers();
        
        request.setAttribute("members", members);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
        }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String memberId = request.getParameter("id");

        if ("delete".equals(action)) {
            try {
                MemberDao2 memberDao = new MemberDao2();
                boolean success = memberDao.deleteMember(memberId);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"success\": " + success + "}");
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"success\": false}");
            }
        }
    }

    private boolean isAdmin(String username) {
        // 관리자 확인 로직 구현. 예를 들어, 특정 아이디만 관리자라고 가정.
        return "admin".equals(username);
    }
}
