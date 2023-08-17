package com.example.taskmanager.repository;

import com.example.taskmanager.entity.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query(value = "select * from Task where user_id = :id", nativeQuery = true)
    List<Task> test(Long id);
}
