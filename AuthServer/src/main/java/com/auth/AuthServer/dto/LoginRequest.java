package com.auth.AuthServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest
{
    private String userName;
    private String password;
    private String email;
    private String role;
}
