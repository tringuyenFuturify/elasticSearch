package io.futurify.frp.commons.models.resquests;

import java.sql.Date;

import io.futurify.frp.commons.models.enums.ObjectStatus;
import io.futurify.frp.commons.models.enums.ProjectStatus;
import io.futurify.frp.models.Project;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectReqModel{		 
	
		private String name;
		
		private Date startDate;
		
		private Date endDate;
		
		private Date releaseDate;
		
		private double budget;
		
		private String requiredSkills;
		
		private String colors;
		
		private ProjectStatus projectStatus;
		
		public Project toProjectModel() {
			
			return Project.builder()
					 	  .name(this.getName())
					 	  .startDate(this.getStartDate())
						  .endDate(this.getEndDate())
						  .releaseDate(this.getReleaseDate())
						  .budget(this.getBudget())
						  .requiredSkills(this.getRequiredSkills())
						  .colors(this.getColors())
						  .projectStatus(this.getProjectStatus())
						  .status(ObjectStatus.Active)
						  .build();
		}
		
}
