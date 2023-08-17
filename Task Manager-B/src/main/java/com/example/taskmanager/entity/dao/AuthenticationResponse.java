package com.example.taskmanager.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
    private String userName;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String token;
    private String roleToken;
}
