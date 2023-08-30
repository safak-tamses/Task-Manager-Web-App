package com.example.taskmanager.service;

import com.example.taskmanager.entity.dao.AuthenticationResponse;
import com.example.taskmanager.entity.dto.LoginWithTokenResponse;
import com.example.taskmanager.entity.dto.UserLoginRequestDTO;
import com.example.taskmanager.entity.dto.UserRegisterRequestDTO;
import com.example.taskmanager.entity.enumS.Role;
import com.example.taskmanager.entity.model.User;

import com.example.taskmanager.error.UserAlreadyExistsException;
import com.example.taskmanager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor

public class UserCRUDService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthenticationResponse saveUser(UserRegisterRequestDTO user){
            Optional<User> testUser = userRepository.findByUserName(user.getUsername());

            if (testUser.isPresent()) {
                throw new UserAlreadyExistsException(user.getUsername());
            }
            User newUser = User.builder()
                    .userName(user.getUsername())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .role(Role.ROLE_USER)
                    .build();

            userRepository.save(newUser);
        var jwtToken = jwtService.generateToken(newUser);
        String privateKey;
        if(newUser.getRole() == Role.ROLE_USER){
            privateKey = "ThWmZq4t7w!z%C&F)J@NcRfUjXn2r5u8x/A?D(G-KaPdSgVkYp3s6v9y$B&E)H@MbQeThWmZq4t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u8x/A?D(G+KbPeShVmYq3t6w9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdSgVkYp3s5v8y/B?E(H+MbQeThWmZq4t7w9z$C&F)J@NcRfUjXn2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRfUjXn2r5u8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)H@McQ";

        }else {
            privateKey = "t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u8x/A?D(G+KbPeShVmYq3t6w9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdSgVkYp3s5v8y/B?E(H+MbQeThWmZq4t7w9z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)H+MbQeThWmZq4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)H@McQfTjWmZq4t7w!z%C*F-JaNdRgUkXp2r5u8x/A?D(G+KbPeShVmYq3t6v9y$B&E)H@McQfTjWnZr";
        }
        return AuthenticationResponse.builder()
                .userName(newUser.getUsername())
                .name(newUser.getName())
                .surname(newUser.getSurname())
                .dateOfBirth(newUser.getDateOfBirth())
                .token(jwtToken)
                .roleToken(privateKey)
                .build();
    }
    public AuthenticationResponse userLogin(UserLoginRequestDTO authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByUserName(authenticationRequest.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        String privateKey;
        if(user.getRole() == Role.ROLE_USER){
            privateKey = "ThWmZq4t7w!z%C&F)J@NcRfUjXn2r5u8x/A?D(G-KaPdSgVkYp3s6v9y$B&E)H@MbQeThWmZq4t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u8x/A?D(G+KbPeShVmYq3t6w9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdSgVkYp3s5v8y/B?E(H+MbQeThWmZq4t7w9z$C&F)J@NcRfUjXn2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRfUjXn2r5u8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)H@McQ";
        }else {
            privateKey = "t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u8x/A?D(G+KbPeShVmYq3t6w9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdSgVkYp3s5v8y/B?E(H+MbQeThWmZq4t7w9z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)H+MbQeThWmZq4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)H@McQfTjWmZq4t7w!z%C*F-JaNdRgUkXp2r5u8x/A?D(G+KbPeShVmYq3t6v9y$B&E)H@McQfTjWnZr";
        }
        return AuthenticationResponse.builder()
                .userName(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .dateOfBirth(user.getDateOfBirth())
                .token(jwtToken)
                .roleToken(privateKey)
                .build();
    }
    public User findUserToToken(String bearerToken) throws Exception {
        try {
            bearerToken = bearerToken.substring(7);
            final String userName = jwtService.extractUsername(bearerToken);
            User user = userRepository.findByUserName(userName).orElseThrow(() -> new Exception("Kullanıcı bulunamadı "));
            return user;
        } catch (Exception e) {
            throw new Exception("Token problem");
        }
    }

    public LoginWithTokenResponse loginWithToken(String bearerToken){
        try {
            bearerToken = bearerToken.substring(7);
            final String userName = jwtService.extractUsername(bearerToken);
            User user = userRepository.findByUserName(userName).orElseThrow(() -> new Exception("Kullanıcı bulunamadı "));
            String updatedToken = jwtService.generateToken(user);
            return LoginWithTokenResponse.builder()
                    .updatedToken(updatedToken)
                    .success(true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
