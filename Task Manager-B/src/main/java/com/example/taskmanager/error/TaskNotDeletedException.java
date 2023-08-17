package com.example.taskmanager.error;

public class TaskNotDeletedException extends RuntimeException{
    public TaskNotDeletedException() {
        super("Task not deleted.! ");
    }
}
