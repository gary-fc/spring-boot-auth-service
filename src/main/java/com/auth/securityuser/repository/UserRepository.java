package com.auth.securityuser.repository;

import com.auth.securityuser.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByAccountId(Long accountId);

    User findByEmail(String email);

    User findByUsername(String username);


}
