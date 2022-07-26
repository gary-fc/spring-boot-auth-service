package com.auth.securityuser.controller;

import com.auth.securityuser.common.responses.UserResponse;
import com.auth.securityuser.common.util.UserResponseBuilder;
import com.auth.securityuser.domain.User;
import com.auth.securityuser.service.UserService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api-user/users/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> index() {
        return userService.listUsers().stream().map(user -> UserResponseBuilder.getInstance(user).buildUserResponse()).collect(Collectors.toList());
    }

    @GetMapping("/byAccountId/{id}")
    public UserResponse getUserByAccountId(@PathVariable(name = "id") long id) {
        return UserResponseBuilder.getInstance(userService.getUserByAccountId(id)).buildUserResponse();
    }

    @DeleteMapping("/{id}")
    public boolean deleteUserById(@PathVariable(name = "id") long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/userExistsByAccountId/{id}")
    public UserResponse userExistsByAccountId(@PathVariable(name = "id") long id) {
        User user = userService.getUserByAccountId(id);
        return UserResponseBuilder.getInstance(user).buildUserResponse();
    }
}
