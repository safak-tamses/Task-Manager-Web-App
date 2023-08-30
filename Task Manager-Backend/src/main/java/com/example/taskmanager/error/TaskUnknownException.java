package com.example.taskmanager.error;

public class TaskUnknownException extends RuntimeException{
    public TaskUnknownException() {
        super("Something gone wrong.");
    }
}
