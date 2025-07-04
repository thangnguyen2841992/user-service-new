package com.regain.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageBirthDay {
    private int userId;

    private String fullName;

    private String email;

    private boolean isStaff;
}
