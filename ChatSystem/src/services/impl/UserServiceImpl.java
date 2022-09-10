package services.impl;

import models.User;
import services.UserService;

import java.util.HashMap;

public class UserServiceImpl implements UserService {

    private static final HashMap<String, User> userDB = new HashMap<>();

    @Override
    public void registerUser(User user) throws Exception {
        final User fetchedUser = userDB.get(user.getUserId());
        if (fetchedUser == null) {
            userDB.put(user.getUserId(), user);
        } else {
            throw new Exception("User already exist");
        }
    }

    @Override
    public void unregisterUser(String userId) throws Exception {
        final User fetchedUser = userDB.get(userId);
        if (fetchedUser == null) {
            throw new Exception("User doesn't exist");
        } else {
            userDB.remove(userId);
        }
    }

    @Override
    public User getUserDetails(String userId) {
        return userDB.get(userId);
    }
}
