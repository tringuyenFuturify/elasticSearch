package io.futurify.frp.commons.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import org.springframework.data.annotation.CreatedDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseObject{
	
	@Column(nullable=true)
	@CreatedDate
	public Date createdAt;

	@Column(nullable=true)
	public Date modifiedAt;
	
	@Column(nullable=true)
	public int createdBy;

	@Column(nullable=true)
	public int modifiedBy;
	
	 @PrePersist
	 protected void prePersist() {
	     if (this.createdAt == null)  createdAt = new Date();
	     if (this.modifiedAt == null) modifiedAt = new Date();
	 }

	 @PreUpdate
	 protected void preUpdate() {
	     this.modifiedAt = new Date();
	 }
	 
	 @PreRemove
	  protected void preRemove() {
	      this.modifiedAt = new Date();
	 }

}
