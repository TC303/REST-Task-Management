package com.portfolio.taskmanagement.repository;

import com.portfolio.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndStatus(Long userId, Task.Status status);
    List<Task> findByUserIdAndPriority(Long userId, Task.Priority priority);
    List<Task> findByUserIdAndCategoryId(Long userId, Long categoryId);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.dueDate BETWEEN :start AND :end")
    List<Task> findTasksByUserAndDueDateBetween(
        @Param("userId") Long userId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );

    @Query("SELECT t FROM Task t JOIN t.tags tag WHERE t.user.id = :userId AND tag.id = :tagId")
    List<Task> findByUserIdAndTagId(@Param("userId") Long userId, @Param("tagId") Long tagId);
}
