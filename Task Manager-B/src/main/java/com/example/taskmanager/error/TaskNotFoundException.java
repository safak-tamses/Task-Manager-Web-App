package com.example.taskmanager.error;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
        super("Task didn't found.");
    }
}
