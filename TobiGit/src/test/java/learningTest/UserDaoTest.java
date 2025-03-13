package learningTest;

import Config.TestConfig.testDao;
import Domain.UserWithLevel;
import learningTest.Dao.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Stream;

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
        dao.deleteAll();
        user1 = new UserWithLevel("0", "진영", "wlsdud", Level.GOLD, 2, 30 );
        user2 = new UserWithLevel("1", "창진", "ckdwls", Level.SILVER, 10, 20 );
        user3 = new UserWithLevel("2", "동완", "ehddhks", Level.BASIC, 20, 10 );
    }

    static Stream<Arguments> generateData() {
        UserWithLevel user1 = new UserWithLevel("0", "진영", "wlsdud", Level.GOLD, 2, 30);
        UserWithLevel user2 = new UserWithLevel("1", "창진", "ckdwls", Level.SILVER, 10, 20);
        return Stream.of(Arguments.of(user1, user2));
    }

    @ParameterizedTest
    @MethodSource("generateData")
    public void checkSameUser(UserWithLevel user1, UserWithLevel user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }

    @Test
    public void delete() {
        dao.deleteAll();
    }

    @Test
    public void addAndGet() {
        dao.add(user1);
        UserWithLevel userget1 = dao.get(user1.getId());
        System.out.println(userget1.getId());
        checkSameUser(userget1, user1);
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

    @Test
    public void update() {
        // user1을 dao에 등록하고
        dao.add(user1);
        // user1의 정보를 수정
        user1.setName("진영장");
        user1.setPassword("wkdwlsdud");
        user1.setLevel(Level.SILVER);
        user1.setLogin(1000);
        user1.setRecommend(999);
        dao.update(user1);
        // 기존의 데이터베이스에 넣은 user와 user1을 비교
        UserWithLevel user1Updated = dao.get(user1.getId());
        checkSameUser(user1, user1Updated);
    }
}

