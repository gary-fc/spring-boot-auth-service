package com.auth.securityuser.services.UserServices.UserService.UserServicesImpl;

import com.auth.securityuser.domain.User;
import com.auth.securityuser.repository.UserRepository;
import com.auth.securityuser.services.UserServices.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByAccountId(Long accountId) {
        return userRepository.findByAccountId(accountId);
    }

    @Override
    public boolean deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean userExistsByAccountId(Long id) {
        return userRepository.existsByAccountId(id);
    }

}
