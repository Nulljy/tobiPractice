package Service;

import Domain.User;
import Repository.InterfaceDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService implements InterfaceService{
    private InterfaceDB db;

    public UserService(InterfaceDB db) {
        this.db = db;
    }

    @Override
    public String add(User user) throws ClassNotFoundException, SQLException {
//        if(!isDuplicated(user.getId())) {
//                return "실패";
//        }
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = db.getConnection();
            // 추가 => db에 add를 하는것 => 중복확인해야함 => 없다면 추가
            String sql = "INSERT INTO users(name, password) VALUES(?, ?)";
            pstmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate(); // query에서 변경하고 오류남
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                user.setId(rs.getString(1));
                return user.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(c, pstmt, rs);
        }
        return null;
    }

    @Override
    public Optional<User> findByName(String userName) throws ClassNotFoundException, SQLException{
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = db.getConnection();
            String sql = "SELECT * FROM USERS WHERE NAME = ?";
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                User user = new User();
                getUser(user, rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(c, pstmt, rs);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) throws ClassNotFoundException, SQLException{
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = db.getConnection();
            String sql = "SELECT * FROM USERS WHERE ID = ?";
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                User user = new User();
                getUser(user, rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(c, pstmt, rs);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findsAll() throws ClassNotFoundException, SQLException{
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            c = db.getConnection();
            String sql = "SELECT * FROM USERS";
            pstmt = c.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<User> users = new ArrayList<>();
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            throw new IllegalStateException();
        } finally {
            db.closeConnection(c, pstmt, rs);
        }
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
