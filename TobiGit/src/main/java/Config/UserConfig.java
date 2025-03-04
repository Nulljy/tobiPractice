package Config;

import Repository.InterfaceDB;
import Repository.MySqlRepository;
import Service.InterfaceService;
import Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class UserConfig {

    @Bean
    public InterfaceService userService() throws ClassNotFoundException{
        UserService userService = new UserService();
        userService.setDataSource(dataSource());
        return userService;
    }

    @Bean
    public InterfaceDB interfaceDB() {
        return new MySqlRepository();
    }

    @Bean
    public DataSource dataSource() throws ClassNotFoundException{
        Class driverClass = Class.forName("com.mysql.cj.jdbc.Driver");
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setUrl("jdbc:mysql://localhost:3306/testdb");
        dataSource.setUsername("root");
        dataSource.setPassword("0913");
        return dataSource;
    }

}
