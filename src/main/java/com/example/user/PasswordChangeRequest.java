package com.example.user;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String username;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
