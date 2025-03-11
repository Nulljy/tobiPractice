package Service.thisIsForTest.Statement;

import Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy{
    User user;

    AddStatement(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        String sql = "INSERT INTO USERS(name, password) VALUES(?, ?)";
        PreparedStatement pstmt = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        // rs를 가져오면 jdbcContext에 전략만 넣어서 중복코드를 없애는것이 안된다. => 어떻게 해결?
        ResultSet rs = pstmt.getGeneratedKeys();
        return pstmt;
    }
}

// 작업 중단
// 이유 => ps를 돌려주기 위한 메서드인데 executeUpdate 전에 어떻게 rs를 초기화?