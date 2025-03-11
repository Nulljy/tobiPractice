package Service.thisIsForTest;

import Service.thisIsForTest.Statement.DeleteAllStatement;
import Service.thisIsForTest.Statement.StatementStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StatementTestTest {
    DataSource dataSource;

    @BeforeEach
    void setDataSource() throws ClassNotFoundException{
        Class driverClass = Class.forName("com.mysql.cj.jdbc.Driver");
        SimpleDriverDataSource sd = new SimpleDriverDataSource();
        sd.setDriverClass(driverClass);
        sd.setUrl("jdbc:mysql://localhost:3306/testdb");
        sd.setUsername("root");
        sd.setPassword("0913");
        dataSource = sd;
    }

    @Test
    void deleteAll() throws SQLException, ClassNotFoundException {

    }

    @Test
    void jdbcContextWithStatementStrategy() throws SQLException, ClassNotFoundException {
        assertNotNull(dataSource, "dataSource null?");
        StatementStrategy st = new DeleteAllStatement();
        try (
                Connection c = dataSource.getConnection();
                PreparedStatement ps = st.makePreparedStatement(c);
        ) {
            ps.executeUpdate();
        }
        System.out.println("테스트가 성공했습니다.");
    }


}