package com.example.demo;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUser(Integer userid);
    void createUser(Integer userid, String username);

    void createUser(String username, String password, Integer usertype);

    void updateUser(Integer userid, String username);
    void deleteUser(Integer userid);

    User getUser(String username, String password, Integer usertype);

    User getUserByToken(String token);
}
