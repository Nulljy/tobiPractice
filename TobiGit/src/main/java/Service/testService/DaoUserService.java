package Service.testService;

import Domain.UserWithLevel;
import learningTest.Dao.UserDao;
import learningTest.Level;
import learningTest.UpgradePolicies.UserLevelUpgrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;


@Service
public class DaoUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserLevelUpgrade userLevelUpgrade;

    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setService(UserDao userDao, UserLevelUpgrade userLevelUpgrade) {
        this.userDao = userDao;
        this.userLevelUpgrade = userLevelUpgrade;
    }

    public void upgradeLevels() {
        List<UserWithLevel> users = userDao.getAll();
        for(UserWithLevel user : users) {
            if(userLevelUpgrade.canUpgrade(user)) {
                userLevelUpgrade.upgradeLevel(user);
                System.out.println(user.getId() + " 레벨 업그레이드 완료!");
                userDao.update(user);
            }
        }
    }


    /**
     *  로그인, 추천을 확인 후
     *  레벨을 올려주는 함수
     *  03-13 리팩토링 해보기 - UserServiceTest
     */
//    public void upgradeLevels() {
//        // 유저 List 얻어오기
//        List<UserWithLevel> users = userDao.getAll();
//        for(UserWithLevel user : users) {
//            if(canUpgrade(user)) {
//                user.upgradeLevel();
//                userDao.update(user);
//            }
//        }
//    }
//    public void upgradeLevels() {
//        // 유저 List 얻어오기
//        List<UserWithLevel> users = userDao.getAll();
//        for(UserWithLevel user : users) {
//            boolean changed = false;
//            // 레벨이 basic이고, 출석 횟수가 50보다 높다면 실버, 실버인데 추천수가 30이 넘으면 골드
//            if(user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
//                user.setLevel(Level.SILVER);
//                changed = true;
//            } else if(user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
//                user.setLevel(Level.GOLD);
//                changed = true;
//            } else if (user.getLevel() == Level.GOLD) { changed = false; }
//            else {changed = false;}
//            if(changed) {userDao.update(user);}
//        }
//    }

    /**
     * 레벨 올리는 조건에 부합하는지 체크하는 함수
     * 근데 user의 레벨을 올리는 것은 user에서 해주는 것이 좋다.
     * 그래서 레벨은 다음 레벨이 존재하는지의 체크만 해주는 것이 좋음
     */
//    public boolean canUpgrade(UserWithLevel user) {
//        Level currentLevel = user.getLevel();
//        switch (currentLevel) {
//            case BASIC:
//                return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
//            case SILVER:
//                return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
//            case GOLD:
//                return false;
//            default: throw new IllegalStateException("Unknown Level: " + currentLevel);
//        }
//    }


}
