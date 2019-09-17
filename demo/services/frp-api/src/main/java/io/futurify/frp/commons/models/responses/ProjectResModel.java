package io.futurify.frp.commons.models.responses;

import java.sql.Date;
import io.futurify.frp.commons.models.BaseObject;
import io.futurify.frp.commons.models.enums.ObjectStatus;
import io.futurify.frp.commons.models.enums.ProjectStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectResModel extends BaseObject {
		
		private Long id;

		private String name;

		private Date startDate;
	    
		private Date endDate;
	    
		private Date releaseDate;
	    
		private double budget;
	    
		private String requiredSkills;
	    
		private String colors;
	    
		private ProjectStatus projectStatus;
	    
		private ObjectStatus status;
	
}
