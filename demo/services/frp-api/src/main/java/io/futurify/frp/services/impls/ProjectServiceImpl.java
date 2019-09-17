package io.futurify.frp.services.impls;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import io.futurify.frp.commons.models.PagedResponse;
import io.futurify.frp.commons.models.enums.ObjectStatus;
import io.futurify.frp.models.Project;
import io.futurify.frp.repositories.ProjectRepository;
import io.futurify.frp.services.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	
	public ProjectServiceImpl(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	@Override
	public Project get(Long id) {
		
		Project project = projectRepository.findById(id).orElse(null);
		
		if(project == null) {
			throw new NullPointerException("Project not found");
		}
		
		return project;
	}

	@Override
	public PagedResponse<Project> search(int page, int size, String keyword) {

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "Id");  
		
		Page<Project> search = projectRepository.findAllByKeyword(keyword,pageable);  
        
        return new PagedResponse<Project>(search.getContent(), search.getNumber(),search.getSize(), search.getTotalElements(), search.getTotalPages(),search.isLast());
	}

	@Override
	public Project create(Project model) {

		Project project = projectRepository.save(model);
		
		return project;
	}

	@Override
	public Project update(Project model) {
		
		Project project = projectRepository.findById(model.getId()).orElse(null);
		
		if(project == null) {
			throw new NullPointerException("Project not found");
		}
		
		project.update(model);
		projectRepository.save(project);
		
		return project;
	}

	@Override
	public Boolean delete(Long id) {
		
		Project project = projectRepository.findById(id).orElse(null);
		
		if(project == null) {
			throw new NullPointerException("Project not found");
		}
		
		project.setStatus(ObjectStatus.Deleted);
		projectRepository.save(project);
		
		return true;
	}

}
