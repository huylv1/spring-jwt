package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUsers() {
        return this.users;
    }

    @Override
    public User getUser(Integer userid) {
        return users.stream()
                .filter(x -> x.getUserid() == userid)
                .findAny()
                .orElse(new User(0, "Not Available"));
    }

    // Dummy users
    public static List<User> users;
    public UserServiceImpl() {
        users = new LinkedList<>();
        users.add(new User(100, "David", new Date()));
        users.add(new User(101, "Peter"));
        users.add(new User(102, "John"));
    }

    @Override
    public void createUser(Integer userid, String username) {
        User user = new User(userid, username);
        this.users.add(user);
    }

    @Override
    public void createUser(String username, String password, Integer usertype){
        User user = new User(username, password, usertype);
        this.users.add(user);
    }

    @Override
    public void updateUser(Integer userid, String username) {
        users.stream()
                .filter(x -> x.getUserid() == userid)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Item not found"))
                .setUsername(username);
    }

    @Override
    public void deleteUser(Integer userid) {

        users.removeIf((User u) -> u.getUserid() == userid);

    }

    @Override
    public User getUser(String username, String password, Integer usertype) {
        return users.stream()
                .filter(x -> x.getUsername() == username && x.getPassword() == password && x.getUsertype() >= usertype)
                .findAny()
                .orElse(new User(0, "Not Available"));
    }

    @Override
    public User getUserByToken(String token){
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SecurityServiceImpl.secretKey))
                .parseClaimsJws(token).getBody();
        if(claims == null || claims.getSubject() == null){
            return null;
        }
        String subject = claims.getSubject();
        if(subject.split("=").length != 2){
            return null;
        }
        String[] subjectParts = subject.split("=");
        Integer usertype = new Integer(subjectParts[1]);
        Integer userid = new Integer(subjectParts[0]);
        return new User(userid, usertype);
    }
}
