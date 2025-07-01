package com.regain.user_service.controller;

import com.regain.user_service.model.ActivationRequest;
import com.regain.user_service.model.User;
import com.regain.user_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-api")
public class UserRestController {
    @Autowired
    private IUserService userService;

    @PostMapping("/activeUser")
    public ResponseEntity<String> activeUser(@RequestBody ActivationRequest activationRequest) {
       String result = this.userService.activeUser(activationRequest.getUserId(), activationRequest.getActiveCode());
       return ResponseEntity.ok(result);
    }
}
