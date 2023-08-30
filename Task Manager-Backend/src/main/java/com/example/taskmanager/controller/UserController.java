package com.example.taskmanager.controller;

import com.example.taskmanager.entity.dto.TaskDeleteDTO;
import com.example.taskmanager.entity.dto.TaskRegisterDTO;
import com.example.taskmanager.entity.dto.TaskResponseDTO;
import com.example.taskmanager.entity.dto.TaskUpdateDTO;
import com.example.taskmanager.service.UserCRUDService;
import com.example.taskmanager.service.UserTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserCRUDService userCRUDService;
    private UserTaskService userTaskService;

    @PostMapping("/addTask")
    public ResponseEntity<TaskResponseDTO> addTask(@RequestHeader("Authorization") String bearerToken,@RequestBody TaskRegisterDTO taskRegisterDTO){
       return new ResponseEntity<>(userTaskService.addTask(bearerToken,taskRegisterDTO), HttpStatusCode.valueOf(200));
    }
    @PutMapping("/updateTask")
    public ResponseEntity<TaskResponseDTO> updateTask(@RequestHeader("Authorization") String bearerToken, @RequestBody TaskUpdateDTO taskUpdateDTO){
        return new ResponseEntity<>(userTaskService.updateTask(bearerToken,taskUpdateDTO),HttpStatusCode.valueOf(200));
    }
    @DeleteMapping("/deleteTask")
    public ResponseEntity<TaskDeleteDTO> deleteTask(@RequestHeader("Authorization") String bearerToken,@RequestParam(value = "id") Long taskId){
        return new ResponseEntity<>(userTaskService.deleteTask(bearerToken,taskId),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/showTasks")
    public ResponseEntity test(@RequestHeader("Authorization") String bearerToken) throws Exception {
        return new ResponseEntity<>(userTaskService.findA(bearerToken), HttpStatusCode.valueOf(200));
    }





}
