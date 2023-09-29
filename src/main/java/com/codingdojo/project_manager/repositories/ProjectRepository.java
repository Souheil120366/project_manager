package com.codingdojo.project_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.codingdojo.project_manager.models.Project;
import com.codingdojo.project_manager.models.User;

import java.util.List;


@Repository
public interface ProjectRepository extends CrudRepository <Project, Long> {
	List<Project> findAll();
	
	List<Project> findByUsersNotContains(User user);
	
	List<Project> findByUsersContains(User user);
}


