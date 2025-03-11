package Domain;

import learningTest.Level;

public class User {
//    Level level;
    String id;
    String name;
    String password;


//    public Level getLevel() {
//        return level;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

//    public void setLevel(Level level) {
//        this.level = level;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
