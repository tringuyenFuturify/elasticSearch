package io.futurify.frp.commons.models.resquests;

import io.futurify.frp.models.Project;
import lombok.Getter;

@Getter
public class UpdateProjectReqModel extends CreateProjectReqModel{
	 public Long id;
	 
	 @Override
	 public Project toProjectModel() {
		 
		 return Project.builder()
				  .id(this.getId())
			 	  .name(this.getName())
			 	  .startDate(this.getStartDate())
				  .endDate(this.getEndDate())
				  .releaseDate(this.getReleaseDate())
				  .budget(this.getBudget())
				  .requiredSkills(this.getRequiredSkills())
				  .colors(this.getColors())
				  .projectStatus(this.getProjectStatus())
				  .build();
	 }
}
