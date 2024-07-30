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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbId = "user0730";
		String dbPw = "pass1234";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbId, dbPw);

		return conn;
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");
    	
    	String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        
        try {
         	Connection conn = getConnection();
          	String sql = "INSERT INTO member (id, password, name, points) VALUES (?, ?, ?, ?)";
        	PreparedStatement statement = conn.prepareStatement(sql);
        	statement.setString(1, username);
       	 	statement.setString(2, password);
     	 	statement.setString(3, name);
         	statement.setInt(4, 1000); 

          	statement.executeUpdate();
               
          	request.setAttribute("registrationMessage", "가입되었습니다. 로그인 해주세요.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

          	statement.close();
         	conn.close();
       	} catch (Exception e) {
           	e.printStackTrace();
        }
    }
}