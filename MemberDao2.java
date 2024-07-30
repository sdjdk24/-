package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Member;

public class MemberDao2 {
	 private Connection getConnection() throws Exception {
	        String driver = "oracle.jdbc.driver.OracleDriver";
	        String url = "jdbc:oracle:thin:@localhost:1521:xe";
	        String dbId = "user0730";
	        String dbPw = "pass1234";
	        
	        Class.forName(driver);
	        return DriverManager.getConnection(url, dbId, dbPw);
	  }

	    public void registerMember(String username, String password, String name) {
	    	Connection conn = null;
	        PreparedStatement stmt = null;
	        try {
	            conn = getConnection();
	            
	            String sql = "INSERT INTO member (id, password, name, points) VALUES (?, ?, ?, ?)";
	            stmt = conn.prepareStatement(sql);
	            
	            stmt.setString(1, username);
	            stmt.setString(2, password);
	            stmt.setString(3, name);
	            stmt.setInt(4, 1000); // 기본 포인트 1000

	            stmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace(); 
	    }       
}
	    public User getUserDetails(String username) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        User user = null;

	        try {
	            conn = getConnection();
	            String query = "SELECT id, name, points FROM member WHERE id = ?";
	            stmt = conn.prepareStatement(query);
	            stmt.setString(1, username);
	            rs = stmt.executeQuery();

	            if (rs.next()) {
	                String id = rs.getString("id");
	                String name = rs.getString("name");
	                int points = rs.getInt("points");
	                user = new User(id, name, points);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return user;
	    }
	 // 회원 목록 조회
	    public List<Member> getAllMembers() {
	        List<Member> members = new ArrayList<>();
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        try {
	            conn = getConnection();
	            String query = "SELECT * FROM member";
	            stmt = conn.prepareStatement(query);
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                String id = rs.getString("id");
	                String password = rs.getString("password");
	                String name = rs.getString("name");
	                int points = rs.getInt("points");

	                members.add(new Member(id, password, name, points));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        return members;
	    }

	    // 회원 삭제
	    public boolean deleteMember(String id) throws Exception {
	        String query = "DELETE FROM member WHERE id = ?";
	        try (Connection conn = getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	             
	            stmt.setString(1, id);
	            return stmt.executeUpdate() > 0;
	        }
	    }
	    public static class User {
	        private String id;
	        private String name;
	        private int points;

	        public User(String id, String name, int points) {
	            this.id = id;
	            this.name = name;
	            this.points = points;
	        }

	        public String getId() {
	            return id;
	        }

	        public String getName() {
	            return name;
	        }

	        public int getPoints() {
	            return points;
	        }
	    }
	    public boolean addPoints(String username, int points) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        int rowsAffected = 0;

	        try {
	            conn = getConnection();
	            String query = "UPDATE member SET points = points + ? WHERE id = ?";
	            stmt = conn.prepareStatement(query);
	            stmt.setInt(1, points);
	            stmt.setString(2, username);

	            rowsAffected = stmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        return rowsAffected > 0;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
