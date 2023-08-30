package com.example.taskmanager.error;

public class TaskRegisterException extends RuntimeException{
    public TaskRegisterException() {
        super("Task doesn't add correctly. Please try again.");
    }
}
