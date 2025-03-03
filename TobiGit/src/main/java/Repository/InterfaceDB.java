package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface InterfaceDB {
    // 디비 어떻게 할건데?
    // 연결을 주고, Service에 전달
    public Connection getConnection() throws ClassNotFoundException, SQLException;
    public void closeConnection(Connection c, PreparedStatement pstmt, ResultSet rs) throws ClassNotFoundException, SQLException;
}