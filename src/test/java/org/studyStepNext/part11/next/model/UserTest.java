package org.studyStepNext.part11.next.model;

public class UserTest {
    public static User newUser(String userId) {
        return new User(userId, "password", "name", "test@sample.com");
    }
}
