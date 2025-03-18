package Service.testService;

import Domain.UserWithLevel;
import learningTest.Dao.UserDao;
import learningTest.UpgradePolicies.UserLevelUpgrade;

import java.util.List;

public class TestUserService extends UserLevelUpgrade {
    private String id;

    public TestUserService(String id) {
        this.id = id;
    }

    public void upgradeLevel(UserWithLevel user) {
        if(user.getId().equals(this.id)) throw new TestUserServiceException();
        super.upgradeLevel(user);
    }

}
