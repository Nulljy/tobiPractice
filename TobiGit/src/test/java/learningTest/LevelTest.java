package learningTest;


import Domain.User;
import Domain.UserWithLevel;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class LevelTest {
    UserWithLevel user1;
    UserWithLevel user2;
    UserWithLevel user3;

    @BeforeEach
    public void setUp() {
        this.user1 = new UserWithLevel("gyumee", "박성철", "springno1", Level.BASIC, 1, 0);
        this.user2 = new UserWithLevel("leegw700", "이길원", "springno2", Level.SILVER, 55, 10);
        this.user3 = new UserWithLevel("bumjin", "박범진", "springno3", Level.GOLD, 100, 40);
    }

    @Test
    public void checkSameUser(){
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }

    @Test
    public void valueOf() {
        Level enT = Level.valueOf(2);
        System.out.println(enT);
    }
}
