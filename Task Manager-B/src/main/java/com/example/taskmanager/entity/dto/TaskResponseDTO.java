package com.example.taskmanager.entity.dto;

import com.example.taskmanager.entity.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    private String username;
    private String title;
    private String subject;
    private String text;
    private String updatedToken;
}
