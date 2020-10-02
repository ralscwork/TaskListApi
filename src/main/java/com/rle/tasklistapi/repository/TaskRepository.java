package com.rle.tasklistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rle.tasklistapi.entidade.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
}
