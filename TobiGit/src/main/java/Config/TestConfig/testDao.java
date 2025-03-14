package Config.TestConfig;

import Domain.User;
import Service.testService.DaoUserService;
import learningTest.Dao.UserDao;
import learningTest.Dao.UserDaoJdbc;
import learningTest.UpgradePolicies.UserLevelUpgrade;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
public class testDao {

    @Bean
    DaoUserService daoUserService() {
        DaoUserService daoUserService = new DaoUserService();
        daoUserService.setService(userDao(), userLevelUpgrade());
        return daoUserService;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/testdb?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("0913");
        return dataSource;
    }

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
        userDaoJdbc.setDataSource(dataSource());
        return userDaoJdbc;
    }

    @Bean
    public UserLevelUpgrade userLevelUpgrade() {
        UserLevelUpgrade userLevelUpgrade = new UserLevelUpgrade();
        return userLevelUpgrade;
    }
}
