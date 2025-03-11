package learningTest;

import Config.TestConfig.testDao;
import Domain.UserWithLevel;
import learningTest.Dao.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = testDao.class)
public class UserDaoTest {
    @Autowired
    private UserDao dao;

    @Autowired
    private DataSource dataSource;

    private UserWithLevel user1;
    private UserWithLevel user2;
    private UserWithLevel user3;

    @BeforeEach
    public void setUp() {
        user1 = new UserWithLevel("0", "진영", "wlsdud", Level.GOLD, 2, 30 );
        user2 = new UserWithLevel("1", "창진", "ckdwls", Level.SILVER, 10, 20 );
        user3 = new UserWithLevel("2", "동완", "ehddhks", Level.BASIC, 20, 10 );
    }

    @Test
    public void duplicateKey() {
        dao.deleteAll();

        dao.add(user1);
        assertThrows(DataAccessException.class, () -> {
            dao.add(user1);
        });
    }

    @Test
    public void sqlExceptionTranslate() {
        dao.deleteAll();

        try {
            dao.add(user2);
            dao.add(user2);
        }
        catch (DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set =
                    new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertThat(set.translate(null, null, sqlEx)).isEqualTo(DuplicateKeyException.class);
        }
    }
}
