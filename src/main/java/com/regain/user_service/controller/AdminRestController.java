package com.regain.user_service.controller;

import com.regain.user_service.model.User;
import com.regain.user_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin-api")
public class AdminRestController {
    @Autowired
    private IUserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(this.userService.getAllUser(), HttpStatus.OK);
    }
}
