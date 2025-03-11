package Service.thisIsForTest;

import Service.thisIsForTest.Statement.DeleteAllStatement;
import Service.thisIsForTest.Statement.StatementStrategy;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementTest {
    DataSource dataSource;
    public void jdbcContextWithStatementStrategy(StatementStrategy statement) throws SQLException{
        try (
                Connection c = dataSource.getConnection();
                PreparedStatement pstmt = statement.makePreparedStatement(c);
        ) {
            pstmt.executeUpdate();
        }
    }

    public void deleteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
    }
}
