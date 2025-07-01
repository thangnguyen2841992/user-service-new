package com.regain.user_service.service;

import com.regain.user_service.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    String activeUser(int userId, String activeCode);

    List<User> getAllUser();
}
