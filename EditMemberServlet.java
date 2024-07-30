package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/EditMemberServlet")
public class EditMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8"); // 요청 인코딩 설정
        response.setContentType("text/html; charset=UTF-8"); // 응답 인코딩 설정
        response.setCharacterEncoding("UTF-8"); // 응답 인코딩 설정
        
    	String id = request.getParameter("id");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        int points = Integer.parseInt(request.getParameter("points"));

        try (Connection conn = getConnection()) {
            String updateQuery = "UPDATE member SET password = ?, name = ?, points = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setString(1, password);
                stmt.setString(2, name);
                stmt.setInt(3, points);
                stmt.setString(4, id);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("회원 정보가 성공적으로 수정되었습니다.");
                } else {
                    System.out.println("회원 정보 수정 실패.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json; charset=UTF-8");
        response.sendRedirect("admin.jsp");
    }

    private Connection getConnection() throws SQLException {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbId = "user0730";
        String dbPw = "pass1234";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("드라이버 로드 실패");
        }
        return DriverManager.getConnection(url, dbId, dbPw);
    }
}
