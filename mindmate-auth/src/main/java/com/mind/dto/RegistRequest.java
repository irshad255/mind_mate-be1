package com.mind.dto;

import lombok.Data;

@Data
public class RegistRequest {
    private String name;
    private String email;
    private String password;
    private String emergencyContact;
}