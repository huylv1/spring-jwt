package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserService userSevice;

    @Autowired
    SecurityService securityService;

    @ResponseBody
    @RequestMapping("")
    public List<User> getAllUsers(){
        return userSevice.getAllUsers();
    }

    @ResponseBody
    @RequestMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id, WebRequest webRequest){
        User user = userSevice.getUser(id);
        long updated = user.getUpdatedDate().getTime();
        boolean isNotModified = webRequest.checkNotModified(updated);

        logger.info("{getUser} isNotModified : " +isNotModified);
        if(isNotModified){
            logger.info("{getUser} resource not modified since last call, so exiting");
            return null;
        }
        logger.info("{getUser} resource modified since last call, so get the updated content");
        return userSevice.getUser(id);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<String, Object> createUser(
            @RequestParam(value="userid") Integer userid,
            @RequestParam(value="username") String username
    ){
        Map<String, Object> map = new LinkedHashMap<>();
        userSevice.createUser(userid, username);
        map.put("result", "added");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Map<String, Object> updateUser(
            @RequestParam(value="userid") Integer userid,
            @RequestParam(value="username") String username
    ){
        Map<String, Object> map = new LinkedHashMap<>();
        userSevice.updateUser(userid, username);
        map.put("result", "updated");
        return map;
    }

    @ResponseBody
    @TokenRequired
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteUser(
            @PathVariable("id") Integer userid) {
        Map<String, Object> map = new LinkedHashMap<>();
        userSevice.deleteUser(userid);
        map.put("result", "deleted");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/register/customer", method = RequestMethod.POST)
    public Map<String, Object> registerCustomer(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        userSevice.createUser(username, password, 1);
        return Util.getSuccessResult();
    }

    @ResponseBody
    @RequestMapping(value = "/register/admin", method = RequestMethod.POST)
    public Map<String, Object> registerAdmin(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        Map<String, Object> map = new LinkedHashMap<>();
        userSevice.createUser(username, password, 3); // 3 - admin (usertype)
        map.put("result", "added");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/register/csr", method = RequestMethod.POST)
    public Map<String, Object> registerCSR(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        userSevice.createUser(username, password, 2);
        return Util.getSuccessResult();
    }

    @ResponseBody
    @RequestMapping(value = "/login/customer", method = RequestMethod.POST)
    public Map<String, Object> loginCustomer(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        User user = userSevice.getUser(username, password, 1);
        if(user == null){
            return Util.getUserNotAvailableError();
        }
        String subject = user.getUserid()+"="+user.getUsertype();
        String token = securityService.createToken(subject, (15 * 1000 * 60)); // 15 minutes expiry time
        return Util.getSuccessResult(token);
    }

    @ResponseBody
    @RequestMapping(value = "/login/admin", method = RequestMethod.POST)
    public Map<String, Object> loginAdmin(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        User user = userSevice.getUser(username, password, 3);
        if(user == null){
            return Util.getUserNotAvailableError();
        }
        String subject = user.getUserid()+"="+user.getUsertype();
        String token = securityService.createToken(subject, (15 * 1000 * 60)); // 15 mins expiry time    
        return Util.getSuccessResult(token);
    }

    @ResponseBody
    @RequestMapping(value = "/login/csr", method = RequestMethod.POST)
    public Map<String, Object> loginCSR(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        User user = userSevice.getUser(username, password, 2);
        if(user == null){
            return Util.getUserNotAvailableError();
        }
        String subject = user.getUserid()+"="+user.getUsertype();
        String token = securityService.createToken(subject, (15 * 1000 * 60)); // 15 mins expiry time

        return Util.getSuccessResult(token);
    }
}
