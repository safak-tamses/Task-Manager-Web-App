package com.example.taskmanager.error;

public class UserLoginException  extends RuntimeException{
    public UserLoginException(String username) {
        super("User information's are wrong!");
    }
}
