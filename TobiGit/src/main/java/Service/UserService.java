package Service;

import Domain.User;
import Repository.InterfaceDB;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// dataSource로 바꾸면서 close 함수를 주석처리 해주었다.
public class UserService implements InterfaceService{
//    private InterfaceDB db;
//
//    public UserService() {}
//
//    public UserService(InterfaceDB db) {
//        this.db = db;
//    }
//
//    public void setDB(InterfaceDB db) {
//        this.db = db;
//    }

    private DataSource db;

    public void setDataSource(DataSource dataSource) {
        this.db = dataSource;
    }

    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM USERS";
        try(
                Connection c = db.getConnection();
                PreparedStatement pstmt = c.prepareStatement(sql);
            )
        {
            pstmt.executeUpdate();
        }
    }

    @Override
    public String add(User user) throws ClassNotFoundException, SQLException {
//        if(!isDuplicated(user.getId())) {
//                return "실패";
//        }
        String sql = "INSERT INTO users(name, password) VALUES(?, ?)";
        try (
                Connection c = db.getConnection();
                PreparedStatement pstmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ){
            // 추가 => db에 add를 하는것 => 중복확인해야함 => 없다면 추가
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
            try(ResultSet rs = pstmt.getGeneratedKeys();) {
                if(rs.next()) {
                    user.setId(rs.getString(1));
                    return user.getId();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        finally {
////            db.closeConnection(c, pstmt, rs);
//
//        }
        return null;
    }

    @Override
    public Optional<User> findByName(String userName) throws ClassNotFoundException, SQLException{
        String sql = "SELECT * FROM USERS WHERE NAME = ?";
        try (
                Connection c = db.getConnection();
                PreparedStatement pstmt = c.prepareStatement(sql);

                ){
            pstmt.setString(1, userName);
            try(ResultSet rs = pstmt.executeQuery();) {
                if(rs.next()) {
                    User user = new User();
                    getUser(user, rs);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        finally {
//            db.closeConnection(c, pstmt, rs);
//        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) throws ClassNotFoundException, SQLException{
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        try (Connection c = db.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);)
        {
            pstmt.setString(1, userId);
            try( ResultSet rs = pstmt.executeQuery();) {
                if(rs.next()) {
                    User user = new User();
                    getUser(user, rs);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        finally {
//            db.closeConnection(c, pstmt, rs);
//        }
        return Optional.empty();
    }

    @Override
    public List<User> findsAll() throws ClassNotFoundException, SQLException{
        String sql = "SELECT * FROM USERS";

        try (Connection c = db.getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);)
        {
            try(ResultSet rs = pstmt.executeQuery();) {
                List<User> users = new ArrayList<>();
                while(rs.next()) {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    users.add(user);
                }
                return users;
            }

        } catch (Exception e) {
            throw new IllegalStateException();
        }
//        finally {
//            db.closeConnection(c, pstmt, rs);
//        }
    }


    public boolean isDuplicated(String userID) throws SQLException, ClassNotFoundException {
        // findsAll에서 list를 받아오고 거기에 userId가 같은게 있는지 확인
        List<User> users = findsAll();
        Optional<User> userItem = users.stream().filter(user -> user.getId().equals(userID)).findAny();
        return userItem.isPresent();
    }

    public void getUser(User user, ResultSet rs) throws SQLException{
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
    }
}
