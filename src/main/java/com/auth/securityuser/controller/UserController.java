package com.auth.securityuser.controller;

import com.auth.securityuser.common.util.UserResponseBuilder;
import com.auth.securityuser.responses.UserResponse;
import com.auth.securityuser.services.UserServices.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin("*")
    @GetMapping
    public List<UserResponse> index() {
        return userService.listUsers().stream().map(user -> UserResponseBuilder.getInstance(user).buildUserResponse()).collect(Collectors.toList());
    }

    @CrossOrigin("*")
    @GetMapping("/byAccountId/{id}")
    public UserResponse getUserByAccountId(@PathVariable(name = "id") long id) {
        return UserResponseBuilder.getInstance(userService.getUserByAccountId(id)).buildUserResponse();
    }

    @CrossOrigin("*")
    @DeleteMapping("/{id}")
    public boolean deleteUserById(@PathVariable(name = "id") long id) {
        return userService.deleteUserById(id);
    }

    @CrossOrigin("*")
    @GetMapping("/userExistsByAccountId/{id}")
    public boolean userExistsByAccountId(@PathVariable(name = "id") long id) {
        return userService.userExistsByAccountId(id);
    }
}
