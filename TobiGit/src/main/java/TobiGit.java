import Config.UserConfig;
import Repository.MySqlRepository;
import Service.InterfaceService;
import Service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
public class TobiGit {
    public static void main(String[] args) {
//        SpringApplication.run(TobiGit.class, args);
//        MySqlRepository mySqlRepository =
//                context.getBean("interfaceDB", MySqlRepository.class);
        ApplicationContext context =
                new AnnotationConfigApplicationContext(UserConfig.class);
        InterfaceService userService =
                context.getBean("userService", UserService.class);
    }
}
