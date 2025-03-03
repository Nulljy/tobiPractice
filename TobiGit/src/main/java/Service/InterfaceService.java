package Service;

import Domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface InterfaceService {
    public String add (User user) throws ClassNotFoundException, SQLException;
    public Optional<User> findByName(String userName) throws ClassNotFoundException, SQLException;
    public Optional<User> findById(String userId) throws ClassNotFoundException, SQLException;
    public List<User> findsAll() throws ClassNotFoundException, SQLException;
}
