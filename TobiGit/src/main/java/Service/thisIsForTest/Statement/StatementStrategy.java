package Service.thisIsForTest.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
    public PreparedStatement makePreparedStatement (Connection c) throws SQLException;
    
}
