import Config.UserConfig;
import Domain.User;
import Repository.MySqlRepository;
import Service.InterfaceService;
import Service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

//@SpringBootApplication
public class TobiGit {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        SpringApplication.run(TobiGit.class, args);
//        MySqlRepository mySqlRepository =
//                context.getBean("interfaceDB", MySqlRepository.class);
        ApplicationContext context =
                new AnnotationConfigApplicationContext(UserConfig.class);
        InterfaceService userService =
                context.getBean("userService", UserService.class);
        User user1 = new User();
        user1.setName("진영");
        user1.setPassword("20051122");
        userService.add(user1);
    }

}
