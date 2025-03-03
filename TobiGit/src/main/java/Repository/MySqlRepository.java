package Repository;

import java.sql.*;

public class MySqlRepository implements InterfaceDB{
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb", "user", "0913"
        );
        return c;
    }

    @Override
    public void closeConnection(Connection c, PreparedStatement pstmt, ResultSet rs) throws ClassNotFoundException, SQLException {
        if(rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(c != null) {
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
