package com.auth.securityuser.repository;

import com.auth.securityuser.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    boolean existsByAccountId(Long accountId);

    User findByAccountId(Long accountId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String username);


}
