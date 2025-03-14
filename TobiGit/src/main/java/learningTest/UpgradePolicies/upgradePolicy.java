package learningTest.UpgradePolicies;

import Domain.UserWithLevel;

public interface upgradePolicy {
    boolean canUpgrade(UserWithLevel user);
    void upgradeLevel(UserWithLevel user);
}
