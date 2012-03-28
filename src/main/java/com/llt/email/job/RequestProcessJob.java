package com.llt.email.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.llt.email.dao.EmailJobDao;
import com.llt.email.model.EmailJob;
import com.llt.email.service.EmailService;
import com.llt.email.util.JobName;
import com.llt.email.util.JobStatus;

public class RequestProcessJob {
	private static final Logger logger = LoggerFactory
			.getLogger(RequestProcessJob.class);

	@Autowired
	EmailService emailServiceImpl;

	@Autowired
	EmailJobDao emailJobDaoImpl;

	public void process() {
		try {
			// 1. verify
			EmailJob emailJob = emailJobDaoImpl
					.getJob(JobName.JOB_SEND_RESPONSE.getCode());

			// Job did not exist - first time
			if (null == emailJob) {
				logger.debug("Job doesn't exist, seems first time.. creating the job");
				emailJob = new EmailJob();
				emailJob.setJobName(JobName.JOB_SEND_RESPONSE.getCode());
				emailJob.setJobStatus(JobStatus.RUNNING.getCode());

				// Create the Job
				emailJobDaoImpl.insertJob(emailJob);

				// Evaluate the requests
				emailServiceImpl.evaluateRequests();

				// Update the Job status back to IDLE
				emailJob.setJobStatus(JobStatus.IDLE.getCode());
				emailJobDaoImpl.updateJob(emailJob);

			} else if (emailJob.getJobStatus().equalsIgnoreCase(
					JobStatus.RUNNING.getCode())) {
				logger.debug("Job is already running...returning..");

			} else if (emailJob.getJobStatus().equalsIgnoreCase(
					JobStatus.IDLE.getCode())) {
				logger.debug("There is no job running now, it will now evaluate the requests..");

				// change the status from IDLE to running
				emailJob.setJobStatus(JobStatus.RUNNING.getCode());
				emailJobDaoImpl.updateJob(emailJob);

				// evaluate requests
				emailServiceImpl.evaluateRequests();

				// update back to IDLE
				emailJob.setJobStatus(JobStatus.IDLE.getCode());
				emailJobDaoImpl.updateJob(emailJob);

			} else {
				logger.debug("Unknown state");
			}

		} catch (Exception e) {
			logger.error(
					"Error occurred while processing the job, "
							+ e.getMessage(), e);
		}
	}

}
