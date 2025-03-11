package learningTest.Dao;

import Domain.User;
import Domain.UserWithLevel;
import learningTest.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.util.List;

public class UserDaoJdbc implements UserDao{
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<UserWithLevel> userMapper = (rs, rowNum) -> new UserWithLevel(
        rs.getString("id"),
        rs.getString("name"),
        rs.getString("password"),
        Level.valueOf(rs.getInt("level")),
        rs.getInt("login"),
        rs.getInt("recommend")
    );

    @Override
    public void add(final UserWithLevel user) {
        this.jdbcTemplate.update("insert into testUsers(id, name, password, level, login, recommend) values(?,?,?,?,?,?)", user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    @Override
    public UserWithLevel get(String id) {
        return this.jdbcTemplate.queryForObject("select * from testUsers where id = ?",
                new Object[] {id}, this.userMapper);
    }

    @Override
    public List<UserWithLevel> getAll() {
        return this.jdbcTemplate.query("select * from testUsers order by id", this.userMapper);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from testUsers");
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from testUsers", Integer.class);
    }
}
