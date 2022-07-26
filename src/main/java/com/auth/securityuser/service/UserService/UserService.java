package com.auth.securityuser.service.UserService;

import com.auth.securityuser.domain.User;
import com.auth.securityuser.dto.UserDTO;

import java.util.List;

public interface UserService {

    User createUser(UserDTO userDTO);

    List<User> listUsers();

    User getUserByAccountId(Long id);

    Boolean deleteUserById(Long id);

    User findByEmail(String email);

    User findByUsername(String username);

    User findByAccountId(Long id);
}
