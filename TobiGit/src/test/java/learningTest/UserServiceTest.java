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

//import static Service.testService.DaoUserService.MIN_LOGCOUNT_FOR_SILVER;
//import static Service.testService.DaoUserService.MIN_RECOMMEND_FOR_GOLD;
import static learningTest.UpgradePolicies.UserLevelUpgrade.MIN_LOGIN_FOR_SILVER;
import static learningTest.UpgradePolicies.UserLevelUpgrade.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = testDao.class)
public class UserServiceTest {
    @Autowired
    DaoUserService daoUserService;

    @Autowired
    private UserDao userDao;

    List<UserWithLevel> users;
    UserWithLevel testUser;

    @BeforeEach
    public void setUp() {
        userDao.deleteAll();
        testUser = new UserWithLevel();
        users = Arrays.asList(
          new UserWithLevel("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGIN_FOR_SILVER-1, 0),
          new UserWithLevel("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGIN_FOR_SILVER, 0),
          new UserWithLevel("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1),
          new UserWithLevel("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
          new UserWithLevel("green", "오민규", "p5", Level.GOLD, 100, MIN_RECOMMEND_FOR_GOLD)
        );
    }


    /**
     * upgrade 관련 메서드를 인터페이스 분리하여 service에 DI해주었다.
     * service로 유저의 레벨을 올려준후 되는지 확인
     */
    @Test
    public void upgradeLevels() {
        for(UserWithLevel user : users) {
            userDao.add(user);
        }
        daoUserService.upgradeLevels();
    }
    /**
     * dao에 users에 있는 모든 user를 추가 후 upgrade 해보기
     * 03-13 리팩토링해보기
     */
//    @Test
//    public void upgradeLevels() {
//        // setUp
//        userDao.deleteAll();
//        // 추가
//        for(UserWithLevel user : users) {
//            userDao.add(user);
//        }
//        daoUserService.upgradeLevels();
//
//        checkLevel(users.get(0), Level.BASIC);
//        checkLevel(users.get(1), Level.SILVER);
//        checkLevel(users.get(2), Level.SILVER);
//        checkLevel(users.get(3), Level.GOLD);
//        checkLevel(users.get(4), Level.GOLD);
//    }

//    @Test
//    public void upgradeLevels() {
//        Level[] levels = Level.values();
//        for(Level level : levels) {
//            if(level.nextLevel() == null) continue;
//            testUser.setLevel(level);
//            testUser.upgradeLevel();
//            // 테스트 유저의 레벨을 1 높인후, level + 1 이랑 같은지 비교 => 테스트 유저의 레벨이 잘 올라갔는지 확인
//            assertThat(testUser.getLevel()).isEqualTo(level.nextLevel());
//        }
//    }

    @Test
    public void canNotUpgradeLevels() {
        Level[] levels = Level.values();
        for(Level level : levels) {
            if(level.nextLevel() != null) continue;
            testUser.setLevel(level);
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
                testUser.upgradeLevel();
            });
            System.out.println(exception.getMessage());

        }
    }

//    private void checkLevel(UserWithLevel user, Level expectedLevel) {
//        UserWithLevel userUpdate = userDao.get(user.getId());
//        assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
//    }
    private void checkLevelUpgraded(UserWithLevel user, boolean upgrade) {
        UserWithLevel userUpdate = userDao.get(user.getId()); // db에서 가져온것
        if (upgrade) {
            // db에 등록된 level이 매개인자로 받은 user의 레벨 + 1인지 확인
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    public void bean() {
        assertThat(this.daoUserService).isNotNull();
        assertThat(this.userDao).isNotNull();
    }

}
