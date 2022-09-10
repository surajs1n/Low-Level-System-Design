package services;

import models.User;

public interface UserService {

    public void registerUser(User user) throws Exception;

    public void unregisterUser(String userId) throws Exception;

    public User getUserDetails(String userId);
}
