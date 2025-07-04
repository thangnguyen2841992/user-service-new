package com.regain.user_service.service;

import com.regain.user_service.model.MessageBirthDay;
import com.regain.user_service.model.MessageResponseUser;
import com.regain.user_service.model.Role;
import com.regain.user_service.model.User;
import com.regain.user_service.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

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
                    MessageResponseUser messageResponseUser = new MessageResponseUser();
                    messageResponseUser.setToUserEmail(user.getEmail());
                    messageResponseUser.setToUserName(user.getUsername());
                    messageResponseUser.setToUserFullName(user.getFirstName() + " " + user.getLastName());
                    messageResponseUser.setToUserId(user.getUserId());
                    kafkaTemplate.send("send-email-active-response", messageResponseUser);
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

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAllUsers();
    }

    @Override
    public List<User> findAllStaff() {
        return this.userRepository.findAllStaff();
    }

    @Scheduled(cron = "0 14 13 * * ?")
    public void checkBirthDay() {
        List<User> users = this.userRepository.findAll();
        Calendar today = Calendar.getInstance();
        int currentDay = today.get(Calendar.DAY_OF_MONTH);
        int currentMonth = today.get(Calendar.MONTH); // Tháng bắt đầu từ 0

        for (User user : users) {
            Calendar birthday = Calendar.getInstance();
            birthday.setTime(user.getBirthday());

            if (birthday.get(Calendar.DAY_OF_MONTH) == currentDay &&
                    birthday.get(Calendar.MONTH) == currentMonth) {
                MessageBirthDay messageBirthDay = new MessageBirthDay();
                messageBirthDay.setUserId(user.getUserId());
                messageBirthDay.setFullName(user.getFirstName() + " " + user.getLastName());
                messageBirthDay.setStaff(true);
                messageBirthDay.setEmail(user.getEmail());
                for (Role role : user.getRoles()) {
                    if (role.getRoleName().equals("ROLE_USER")) {
                        messageBirthDay.setStaff(false);
                    }
                }
                kafkaTemplate.send("send-email-birthday-response", messageBirthDay);
            }
        }
    }

}
