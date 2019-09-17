package io.futurify.frp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.futurify.frp.models.Project;

@Repository("projectRepository")
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	 @Query(value = "SELECT * FROM PROJECT WHERE NAME like %:keyword%",
			countQuery = "SELECT count(*) FROM PROJECT WHERE NAME like %:keyword%",
			nativeQuery = true)
	Page<Project> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
}