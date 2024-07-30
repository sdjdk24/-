package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection getConnection() throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbId = "user0730";
        String dbPw = "pass1234";
        Class.forName(driver);
        return DriverManager.getConnection(url, dbId, dbPw);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && password != null) {
            try {
                Connection conn = getConnection();
                String sql = "SELECT * FROM member WHERE id = ? AND password = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);

                    if (username.equals("admin")) {
                        response.sendRedirect("admin.jsp");
                    } else {
                        response.sendRedirect("main.jsp");
                    }
                } else {
                    request.setAttribute("loginError", "아이디/비밀번호를 다시 확인하세요");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }

                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("loginError", "로그인 중 오류가 발생했습니다.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("loginError", "모든 필드를 입력해 주세요.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
