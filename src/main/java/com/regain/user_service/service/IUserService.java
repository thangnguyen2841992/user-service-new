package com.regain.user_service.service;

import com.regain.user_service.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends UserDetailsService {
    String activeUser(int userId, String activeCode);

    List<User> getAllUser();

    Optional<User> findByUserId(int  userId);

    User saveUser(User user);

    List<User> findAllUsers();
}
