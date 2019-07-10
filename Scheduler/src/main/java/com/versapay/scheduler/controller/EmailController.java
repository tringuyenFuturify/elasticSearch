package com.versapay.scheduler.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.versapay.scheduler.model.JobDescriptor;
import com.versapay.scheduler.service.SchedulerService;

@RestController
@RequestMapping("/api/v1.0")
public class EmailController {

	@Autowired
	private SchedulerService schedulerService;

	@PostMapping(path = "/groups/{group}/jobs")
	public ResponseEntity<JobDescriptor> createJob(@PathVariable String group, @RequestBody JobDescriptor descriptor) {
		return new ResponseEntity<>(schedulerService.createJob(group, descriptor), CREATED);
	}

	@GetMapping(path = "/groups/{group}/jobs/{name}")
	public ResponseEntity<JobDescriptor> findJob(@PathVariable String group, @PathVariable String name) {
		return schedulerService.findJob(group, name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping(path = "/groups/{group}/jobs/{name}")
	public ResponseEntity<Void> updateJob(@PathVariable String group, @PathVariable String name,
			@RequestBody JobDescriptor descriptor) {
		schedulerService.updateJob(group, name, descriptor);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(path = "/groups/{group}/jobs/{name}")
	public ResponseEntity<Void> deleteJob(@PathVariable String group, @PathVariable String name) {
		schedulerService.deleteJob(group, name);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(path = "/groups/{group}/jobs/{name}/pause")
	public ResponseEntity<Void> pauseJob(@PathVariable String group, @PathVariable String name) {
		schedulerService.pauseJob(group, name);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(path = "/groups/{group}/jobs/{name}/resume")
	public ResponseEntity<Void> resumeJob(@PathVariable String group, @PathVariable String name) {
		schedulerService.resumeJob(group, name);
		return ResponseEntity.noContent().build();
	}
}
