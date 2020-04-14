package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {
    @Autowired
    UserService userSevice;
    @Test
    public void testAllUsers(){
        List<User> users = userSevice.getAllUsers();
        assertEquals(3, users.size());
    }

    @Test
    public void testSingleUser(){
        User user = userSevice.getUser(100);
        assertTrue(user.getUsername().contains("David"));
    }
}
