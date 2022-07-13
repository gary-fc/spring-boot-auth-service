package com.auth.securityuser.services.UserServices.UserService;

import com.auth.securityuser.domain.User;

import java.util.List;

public interface UserService {

    List<User> listUsers();

    User getUserByAccountId(Long id);

    boolean deleteUserById(Long id);

    boolean userExistsByAccountId(Long id);
}
