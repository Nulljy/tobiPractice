package learningTest;

import Config.TestConfig.testDao;
import Domain.UserWithLevel;
import Service.testService.DaoUserService;
import learningTest.Dao.UserDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = testDao.class)
public class UserServiceTest {
    @Autowired
    DaoUserService daoUserService;

    @Autowired
    private UserDao userDao;

    List<UserWithLevel> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
          new UserWithLevel("bumjin", "박범진", "p1", Level.BASIC, 49, 0),
          new UserWithLevel("joytouch", "강명성", "p2", Level.BASIC, 50, 0),
          new UserWithLevel("erwins", "신승한", "p3", Level.SILVER, 60, 29),
          new UserWithLevel("madnite1", "이상호", "p4", Level.SILVER, 60, 30),
          new UserWithLevel("green", "오민규", "p5", Level.GOLD, 100, 100)
        );
    }

    /**
     * dao에 users에 있는 모든 user를 추가 후 upgrade 해보기
     * 03-13 리팩토링해보기
     */
    @Test
    public void upgradeLevels() {
        // setUp
        userDao.deleteAll();
        // 추가
        for(UserWithLevel user : users) {
            userDao.add(user);
        }
        daoUserService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(UserWithLevel user, Level expectedLevel) {
        UserWithLevel userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    public void bean() {
        assertThat(this.daoUserService).isNotNull();
        assertThat(this.userDao).isNotNull();
    }

}
