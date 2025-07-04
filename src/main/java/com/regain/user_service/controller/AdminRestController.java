package com.regain.user_service.controller;

import com.regain.user_service.model.User;
import com.regain.user_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin-api")
public class AdminRestController {
    @Autowired
    private IUserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(this.userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/getAllStaff")
    public ResponseEntity<List<User>> getAllStaff() {
        return new ResponseEntity<>(this.userService.findAllStaff(), HttpStatus.OK);
    }

    @PostMapping("/blockUser")
    public ResponseEntity<User> blockUser(@RequestBody User user ) {
        Optional<User> userOptional = this.userService.findByUserId(user.getUserId());
        if(userOptional.isPresent()) {
            userOptional.get().setBlock(true);
            this.userService.saveUser(userOptional.get());
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST);
    }
}
