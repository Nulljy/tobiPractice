package Config;

import Repository.InterfaceDB;
import Repository.MySqlRepository;
import Service.InterfaceService;
import Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public InterfaceService userService() {
        return new UserService(interfaceDB());
    }

    @Bean
    public InterfaceDB interfaceDB() {
        return new MySqlRepository();
    }
}
