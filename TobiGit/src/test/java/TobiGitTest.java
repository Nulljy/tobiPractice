import Config.UserConfig;
import Domain.User;
import Service.InterfaceService;
import Service.UserService;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TobiGitTest {
    private UserService userService;

    @BeforeEach
    public void setUp() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(UserConfig.class);
        this.userService =
                context.getBean("userService", UserService.class);
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        User user1 = new User();
        user1.setName("창진");
        user1.setPassword("20050815");
        userService.add(user1);

        Optional<User> user2 = userService.findById(user1.getId());

    }
}