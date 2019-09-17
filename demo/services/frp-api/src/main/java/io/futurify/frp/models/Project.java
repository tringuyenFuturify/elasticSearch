package io.futurify.frp.models;

import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import io.futurify.frp.commons.models.BaseObject;
import io.futurify.frp.commons.models.enums.ObjectStatus;
import io.futurify.frp.commons.models.enums.ProjectStatus;
import io.futurify.frp.commons.models.responses.ProjectResModel;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class Project extends BaseObject {
		
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private String name;

	    private Date startDate;
	    
	    private Date endDate;
	    
	    private Date releaseDate;
	    
	    private double budget;
	    
	    private String requiredSkills;
	    
	    private String colors;
	    
	    private ProjectStatus projectStatus;
	    
	    private ObjectStatus status;

	    public ProjectResModel toProjectResModel() {
			 
			 return ProjectResModel.builder()
					  .id(this.getId())
				 	  .name(this.getName())
				 	  .startDate(this.getStartDate())
					  .endDate(this.getEndDate())
					  .releaseDate(this.getReleaseDate())
					  .budget(this.getBudget())
					  .requiredSkills(this.getRequiredSkills())
					  .colors(this.getColors())
					  .projectStatus(this.getProjectStatus())
					  .status(this.getStatus())
					  .build();
		 }
		
		public void update(Project model) {
	        this.setName(model.name);
	        this.setStartDate(model.startDate);
	        this.setEndDate(model.endDate);
	        this.setReleaseDate(model.releaseDate);
	        this.setBudget(model.budget);
	        this.setRequiredSkills(model.requiredSkills);
	        this.setColors(model.colors);
	        this.setProjectStatus(model.projectStatus);
	    }
}
