package com.regain.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponseUser {
    private int toUserId;

    private String toUserEmail;

    private String toUserName;

    private String toUserFullName;


}
