package io.futurify.frp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.futurify.frp.commons.models.PagedResponse;
import io.futurify.frp.commons.models.responses.ProjectResModel;
import io.futurify.frp.commons.models.resquests.CreateProjectReqModel;
import io.futurify.frp.commons.models.resquests.UpdateProjectReqModel;
import io.futurify.frp.models.Project;
import io.futurify.frp.services.ProjectService;


@RestController
@RequestMapping("/api/project")
public class ProjectController {

	private final ProjectService projectService;

	ProjectController(ProjectService projectService) {
	    this.projectService = projectService;
	}
	
	@GetMapping("{id}")
	public ProjectResModel get(@PathVariable("id") Long id) {
		
		Project response = projectService.get(id);
		
		return response.toProjectResModel();
	}
	
	@GetMapping
	public  PagedResponse<ProjectResModel> search(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("keyword") String keyword) {
		
		PagedResponse<Project> response = projectService.search(page, size, keyword);
		
		List<ProjectResModel> res = response.getContent().stream().map(x ->x.toProjectResModel()).collect(Collectors.toList());
		
		return new PagedResponse<>( res , response.getPage(),response.getSize(), response.getTotalElements(), response.getTotalPages(),response.isLast());
	}
	
	@PostMapping("create")
    public ProjectResModel create(@RequestBody CreateProjectReqModel model) {
        
        Project project = model.toProjectModel();
        
        Project response = projectService.create(project);
        
        return response.toProjectResModel();
    }
	
	 @PutMapping("update")
	 public ProjectResModel update(@RequestBody UpdateProjectReqModel model) {
		 
	    Project project = model.toProjectModel();
        
        Project response = projectService.update(project);
        
        return response.toProjectResModel();
	 }
	 
	 @DeleteMapping("{id}")
	 public Boolean delete(@PathVariable("id") Long id) {
		 
		 Boolean response = projectService.delete(id);
		 
		 return response;
	 }
}