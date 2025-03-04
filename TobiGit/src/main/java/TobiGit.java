import Config.UserConfig;
import Domain.User;
import Repository.MySqlRepository;
import Service.InterfaceService;
import Service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

//@SpringBootApplication
public class TobiGit {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        SpringApplication.run(TobiGit.class, args);
//        MySqlRepository mySqlRepository =
//                context.getBean("interfaceDB", MySqlRepository.class);
        // 여기가 ApllicationContext
        ApplicationContext context =
                new AnnotationConfigApplicationContext(UserConfig.class);
        InterfaceService userService =
                context.getBean("userService", UserService.class);
        // 여기가 GenericXmlApplicationContext
//        ApplicationContext context =
//                new GenericXmlApplicationContext("applicationConfig.xml");
//        InterfaceService userService = context.getBean("userService", InterfaceService.class);
        User user1 = new User();
        user1.setName("진영");
        user1.setPassword("20051122");
        userService.add(user1);
    }
    
    // 3/4 GenericXmlApplication 적용 완료
    // DataSource 적용하기

}
