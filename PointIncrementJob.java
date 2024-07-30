package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PointIncrementJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try (Connection conn = getConnection()) {
            String updateQuery = "UPDATE member SET points = points + 1";
            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                int updatedCount = stmt.executeUpdate();
                System.out.println("Job이 실행되어, " + updatedCount + "명에게 1포인트가 부여되었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new JobExecutionException("포인트 증가 작업 실패", e);
        }
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
