package io.futurify.frp.services;

import io.futurify.frp.commons.models.PagedResponse;
import io.futurify.frp.models.Project;

public interface ProjectService {
	
	Project get (Long id);
	
	PagedResponse<Project> search (int page, int size, String keyword);
	
	Project create (Project model);
	
	Project update (Project model);
	
	Boolean delete (Long id);
}
