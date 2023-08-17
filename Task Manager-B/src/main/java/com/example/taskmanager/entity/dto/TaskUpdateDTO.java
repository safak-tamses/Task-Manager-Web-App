package com.example.taskmanager.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateDTO {
    private Long taskId;
    private Long userId;
    private String title;
    private String subject;
    private String text;
}
