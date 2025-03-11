package learningTest.Dao;

import Domain.User;
import Domain.UserWithLevel;

import java.util.List;

public interface UserDao {
    void add(UserWithLevel user);
    UserWithLevel get(String id);
    List<UserWithLevel> getAll();
    void deleteAll();
    int getCount();
}
