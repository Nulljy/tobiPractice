package Service.testService;

import Domain.UserWithLevel;
import learningTest.Dao.UserDao;
import learningTest.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DaoUserService {
    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     *  로그인, 추천을 확인 후
     *  레벨을 올려주는 함수
     *  03-13 리팩토링 해보기 - UserServiceTest
     */
    public void upgradeLevels() {
        // 유저 List 얻어오기
        List<UserWithLevel> users = userDao.getAll();
        for(UserWithLevel user : users) {
            boolean changed = false;
            // 레벨이 basic이고, 출석 횟수가 50보다 높다면 실버, 실버인데 추천수가 30이 넘으면 골드
            if(user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
                user.setLevel(Level.SILVER);
                changed = true;
            } else if(user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
                user.setLevel(Level.GOLD);
                changed = true;
            } else if (user.getLevel() == Level.GOLD) { changed = false; }
            else {changed = false;}
            if(changed) {userDao.update(user);}
        }
    }

    /**
     * 레벨 올리는 조건에 부합하는지 체크하는 함수
     */
    public void canUpgrade(UserWithLevel user) {

    }

}
