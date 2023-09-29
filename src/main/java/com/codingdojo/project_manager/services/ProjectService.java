package com.codingdojo.project_manager.services;

import org.springframework.stereotype.Service;

import com.codingdojo.project_manager.models.User;
import com.codingdojo.project_manager.models.Project;
import com.codingdojo.project_manager.repositories.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProjectService {
	
	// adding the Project repository as a dependency
		private final ProjectRepository projectRepository;

		public ProjectService(ProjectRepository projectRepository) {
			 this.projectRepository = projectRepository;
		}

		// returns all the projects
		public List<Project> allProjects() {
			return projectRepository.findAll();
		}
		
		public List<Project> projectsUserIn(User user) { 
			return projectRepository.findByUsersContains(user);
		}
		
		public List<Project> projectsUserNotIn(User user) { 
			return projectRepository.findByUsersNotContains(user);
		}
		
		public List<Project> findProjectsWhereUserNotMemberOf(User user) {
	        // Fetch all projects from the repository
	        List<Project> allProjects = projectRepository.findAll();

	        // Filter projects where the user is not a member
	        List<Project> filteredProjects = allProjects.stream()
	                .filter(project -> !project.getUsers().contains(user))
	                .collect(Collectors.toList());

	        return filteredProjects;
	    }

		// creates a project
		public Project createProject(Project p) {
			return projectRepository.save(p);
		}

		// retrieves a project
		public Project findProject(Long id) {
			Optional<Project> optionalProject = projectRepository.findById(id);
			if (optionalProject.isPresent()) {
				return optionalProject.get();
			} else {
				return null;
			}
		}

		public Project updateProject(Project project) {

			return projectRepository.save(project);
		}

		// to delete a project, we simply pass in the project ID and if our repository finds
		// it, the project will be deleted
		public void deleteProject(Long id) {
			Optional<Project> optionalProject = projectRepository.findById(id);
			if (optionalProject.isPresent()) {
				projectRepository.deleteById(id);
			}
		}

}
