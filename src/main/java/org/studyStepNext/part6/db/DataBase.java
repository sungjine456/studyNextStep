package org.studyStepNext.part6.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import org.studyStepNext.part6.model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
    
    public static void updateUser(User user){
    	users.put(user.getUserId(), user);
    }
}
