package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteMemberServlet")
public class DeleteMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection getConnection() throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbId = "user0730";
        String dbPw = "pass1234";
        Class.forName(driver);
        return DriverManager.getConnection(url, dbId, dbPw);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        try (Connection conn = getConnection()) {
            String query = "DELETE FROM member WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, id);
                stmt.executeUpdate();
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"success\": true}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"success\": false}");
        }
    }
}