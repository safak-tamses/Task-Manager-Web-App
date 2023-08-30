package com.example.taskmanager.service;

import com.example.taskmanager.entity.dto.TaskDeleteDTO;
import com.example.taskmanager.entity.dto.TaskRegisterDTO;
import com.example.taskmanager.entity.dto.TaskResponseDTO;
import com.example.taskmanager.entity.dto.TaskUpdateDTO;
import com.example.taskmanager.entity.model.Task;
import com.example.taskmanager.entity.model.User;
import com.example.taskmanager.error.TaskNotDeletedException;
import com.example.taskmanager.error.TaskNotFoundException;
import com.example.taskmanager.error.TaskRegisterException;
import com.example.taskmanager.error.TaskUnknownException;
import com.example.taskmanager.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserTaskService {
    private final UserCRUDService userCRUDService;
    private final TaskRepository taskRepository;
    private final JwtService jwtService;
    public TaskResponseDTO addTask(String bearerToken, TaskRegisterDTO taskRegisterDTO){
        try {
            User user = userCRUDService.findUserToToken(bearerToken);
            Task newTask = Task.builder()
                    .user(user)
                    .subject(taskRegisterDTO.getSubject())
                    .title(taskRegisterDTO.getTitle())
                    .text(taskRegisterDTO.getText())
                    .creationDate(LocalDate.now())
                    .updateDate(LocalDate.now())
                    .build();
            taskRepository.save(newTask);
            TaskResponseDTO taskResponseDTO = TaskResponseDTO.builder()
                    .subject(newTask.getSubject())
                    .username(newTask.getUser().getUsername())
                    .title(newTask.getTitle())
                    .text(newTask.getText())
                    .updatedToken(jwtService.generateToken(user))
                    .build();

            return taskResponseDTO;
        } catch (Exception e) {
            throw new TaskRegisterException();
        }
    }

    public TaskResponseDTO updateTask(String bearerToken, TaskUpdateDTO taskUpdateDTO){
        try {
            User user = userCRUDService.findUserToToken(bearerToken);
            if (taskUpdateDTO.getUserId().equals(user.getId())){
                Task tempTask = taskRepository.findById(taskUpdateDTO.getTaskId()).orElseThrow(TaskNotFoundException::new);
                tempTask.setTitle(taskUpdateDTO.getTitle());
                tempTask.setSubject(taskUpdateDTO.getSubject());
                tempTask.setText(taskUpdateDTO.getText());
                tempTask.setUpdateDate(LocalDate.now());
                taskRepository.save(tempTask);

                TaskResponseDTO taskResponseDTO = TaskResponseDTO.builder()
                        .username(tempTask.getUser().getUsername())
                        .title(tempTask.getTitle())
                        .subject(tempTask.getSubject())
                        .text(tempTask.getText())
                        .updatedToken(jwtService.generateToken(user))
                        .build();

                return taskResponseDTO;
            } else {
                throw new TaskUnknownException();
            }

        } catch (Exception e) {
            throw new TaskUnknownException();
        }
    }

    public TaskDeleteDTO deleteTask(String bearerToken, Long taskId){
        try {
            User user = userCRUDService.findUserToToken(bearerToken);
            Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
            if (user.getId().equals(task.getUser().getId())) {
                taskRepository.deleteById(taskId);
            } else {
                throw new TaskNotFoundException();
            }
            return TaskDeleteDTO.builder()
                    .updatedToken(jwtService.generateToken(user))
                    .build();
        } catch (Exception e){
            throw new TaskNotDeletedException();
        }
    }
    public List<Task> findA(String bearerToken) throws Exception {
        return taskRepository.test(userCRUDService.findUserToToken(bearerToken).getId());
    }



}
