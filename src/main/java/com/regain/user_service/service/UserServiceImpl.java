package com.regain.user_service.service;

import com.regain.user_service.model.Role;
import com.regain.user_service.model.User;
import com.regain.user_service.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), rolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    @Override
    public String activeUser(int userId, String activeCode) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (activeCode.equals(user.getCodeActive())) {
                if (user.isActive()) {
                    return "Tài khoản đã được kích hoạt!";
                } else {
                    user.setActive(true);
                    this.userRepository.save(user);
                    return "Kích hoạt tài khoản thành công!";
                }
            }
        }
        return "Tài khoản không tồn tại!";
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findByUserId(int userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }
}
