package com.auth.securityuser.service.UserService.UserServicesImpl;

import com.auth.securityuser.domain.Role;
import com.auth.securityuser.domain.User;
import com.auth.securityuser.dto.UserDTO;
import com.auth.securityuser.repository.RoleRepository;
import com.auth.securityuser.repository.UserRepository;
import com.auth.securityuser.service.UserService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = this.composeUser(userDTO);

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        return userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByAccountId(Long accountId) {
        return userRepository.findByAccountId(accountId);
    }

    @Override
    public Boolean deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByAccountId(Long id) {
        return userRepository.findByAccountId(id);
    }

    private User composeUser(UserDTO userDTO) {
        User user = new User();
        user.setAccountId(userDTO.getAccountId());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setCreatedDate(new Date());
        user.setIsDeleted(false);
        return user;
    }
}
