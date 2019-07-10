package com.versapay.scheduler.service;

import java.util.Optional;

import com.versapay.scheduler.model.JobDescriptor;

public interface SchedulerService {

	public JobDescriptor createJob(String group, JobDescriptor descriptor);

	public Optional<JobDescriptor> findJob(String group, String name);

	public void updateJob(String group, String name, JobDescriptor descriptor);

	public void deleteJob(String group, String name);

	public void pauseJob(String group, String name);

	public void resumeJob(String group, String name);

}