package com.codingdojo.project_manager.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.project_manager.models.Project;
import com.codingdojo.project_manager.models.LoginUser;
import com.codingdojo.project_manager.models.User;
import com.codingdojo.project_manager.services.ProjectService;
import com.codingdojo.project_manager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller

public class MainController {

	@Autowired
	private UserService userServ;
	
	@Autowired
	ProjectService projectService;
	
	
	@GetMapping("/")
	public String index(Model model) {

		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "index.jsp";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
			HttpSession session) {

		userServ.register(newUser, result);

		if (result.hasErrors()) {

			model.addAttribute("newLogin", new LoginUser());
			return "index.jsp";
		}

		session.setAttribute("userId", newUser.getId());

		return "redirect:/dashboard";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model,
			HttpSession session) {

		User user = userServ.login(newLogin, result);

		if (result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "index.jsp";
		}

		session.setAttribute("userId", user.getId());

		return "redirect:/dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.setAttribute("userId", null);

		return "redirect:/";
	}

	@GetMapping("/dashboard")
	public String home(Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}

		User user = userServ.findById(userId);
		List<Project> projectsUserNotIn = projectService.projectsUserNotIn(user);
		List<Project> projectsUserIn = projectService.projectsUserIn(user);
		model.addAttribute("projectsUserNotIn",projectsUserNotIn);
		model.addAttribute("projectsUserIn",projectsUserIn);
		model.addAttribute("user", user);
		return "dashboard.jsp";
	}
	
	@GetMapping("/projects/new")
	public String newProject(@ModelAttribute("project") Project project, Model model, HttpSession session) {
		
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}

		User user = userServ.findById(userId);
		model.addAttribute("user", user);
		
		return "new_project.jsp";
	}
	
	@PostMapping("/new/project")
	public String create(
			@Valid @ModelAttribute("project") Project project,
			BindingResult result,
			HttpSession session,
			@RequestParam("due_date") String dueDateStr) {
		
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}
		
    	
    	// Manually parse the date input from the form into a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDate;
        try {
            dueDate = dateFormat.parse(dueDateStr);
        } catch (ParseException e) {
            result.rejectValue("due_date", "InvalidDateFormat", "Invalid date format");
            return "new_project.jsp";
        }

        // Manually validate the due date
        Date currentDate = new Date();
        if (dueDate.before(currentDate)) {
            result.rejectValue("due_date", "PastDueDate", "Due date must be in the future");
            return "new_project.jsp";
        }

        if (result.hasErrors()) {
            return "new_project.jsp";
        } else {
            projectService.createProject(project);
            
    		User user = userServ.findById(userId);
    		user.getProjects().add(project);
    		
            userServ.updateUser(user);
            
            return "redirect:/dashboard";
        }
    }
	
		
	@GetMapping("/projects/{projectId}")
	public String oneProject (Model model,@PathVariable("projectId") Long projectId, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}

		User user = userServ.findById(userId);
		model.addAttribute("user", user);

		Project project = projectService.findProject(projectId);
		model.addAttribute("project",project);
		
		return "show_project.jsp";
	}
	
	@RequestMapping("/delete/{projectId}")
	public String deleteProject(@PathVariable("projectId") Long projectId, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}

		//User user = userServ.findById(userId);
		//Project project = projectService.findProject(projectId);
		
		//project.getUsers().remove(user);
		projectService.deleteProject(projectId);
		return "redirect:/dashboard";
	}
	
	@RequestMapping("/projects/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}

		User user = userServ.findById(userId);
		model.addAttribute("user", user);
		
        Project project = projectService.findProject(id);
        if ( project != null) {
        	        	
           model.addAttribute("project", project);
           return "edit_project.jsp";
        }
        
        else return "redirect:/dashboard";
    }
    
    @PostMapping(value="/projects/{id}/edit")
    public String updateProject(
    		@Valid @ModelAttribute("project") Project project, 
    		@PathVariable("id") Long id, BindingResult result,
    		@RequestParam("due_date") String dueDateStr,
    		HttpSession session) {
    	
    	Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		}
		
    	
    	// Manually parse the date input from the form into a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDate;
        try {
            dueDate = dateFormat.parse(dueDateStr);
        } catch (ParseException e) {
            result.rejectValue("due_date", "InvalidDateFormat", "Invalid date format");
            return "edit_project.jsp";
        }

        // Manually validate the due date
        Date currentDate = new Date();
        if (dueDate.before(currentDate)) {
            result.rejectValue("due_date", "PastDueDate", "Due date must be in the future");
            return "edit_project.jsp";
        }

    	if (result.hasErrors()) {
        	System.out.println("++++++++++++++++++++++++ there is error +++++++++++++++++++++");
            return "edit_project.jsp";
        } else {
        	
        	User user = userServ.findById(userId);
    		Project thisProject = projectService.findProject(id);
    		
    		project.setDue_date(dueDate);
    		    		    		
        	project.setUsers(thisProject.getUsers());
        	project.setLead(user);
            projectService.updateProject(project);
            return "redirect:/dashboard";
        }
    }
    
    @RequestMapping(value="/projects/join/{projectId}/{userId}")
    public String joinProject(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId) {
        	Project project = projectService.findProject(projectId);
        	User user = userServ.findById(userId);
        	project.getUsers().add(user);
            projectService.updateProject(project);
            return "redirect:/dashboard";
       
    }
    
    @RequestMapping(value="/projects/leave/{projectId}/{userId}")
    public String leaveProject(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId) {
        	Project project = projectService.findProject(projectId);
        	User user = userServ.findById(userId);
        	project.getUsers().remove(user);
            projectService.updateProject(project);
            return "redirect:/dashboard";
       
    }

}
