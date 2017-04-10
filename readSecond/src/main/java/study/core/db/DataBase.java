package study.core.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import study.next.model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();
    
    static {
        users.put("admin", new User("admin", "password", "admin", "admin@nav.com"));
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
