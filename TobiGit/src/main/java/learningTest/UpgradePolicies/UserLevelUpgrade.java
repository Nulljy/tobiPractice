package learningTest.UpgradePolicies;

import Domain.UserWithLevel;
import learningTest.Dao.UserDaoJdbc;
import learningTest.Level;

public class UserLevelUpgrade implements upgradePolicy{
    public static final int  MIN_LOGIN_FOR_SILVER = 30;
    public static final int  MIN_RECOMMEND_FOR_GOLD = 50;

    @Override
    public void upgradeLevel(UserWithLevel user) {
        // 레벨을 업그레이드 시켜줌
        // canUpgrade를 통해서 확인 후 다음이 있다면 업그레이드시켜줌
        boolean canUpgraded = canUpgrade(user);
        Level currentLevel = user.getLevel();
        if(canUpgraded) {
            user.setLevel(currentLevel.nextLevel());
        }
    }

    @Override
    public boolean canUpgrade(UserWithLevel user) {
        // 업그레이드가 가능한지?
        // 유저를 받아와서, 유저의 레벨 다음이 있는지 확인하면 됨
        Level currentLevel = user.getLevel();
        switch(currentLevel) {
            case BASIC :
                return (user.getLogin() >= MIN_LOGIN_FOR_SILVER);
            case SILVER:
                return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD:
                return false;
            default: throw new IllegalStateException(currentLevel + "은 unknown level입니다.");
        }
    }
}
