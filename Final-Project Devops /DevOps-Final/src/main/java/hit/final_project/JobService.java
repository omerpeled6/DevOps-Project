package hit.final_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // Marks this class as a service component in the Spring context

// מקבל רפרס לריפוסיטורי ומזריק לקונסטרקטור ומגדיר את המתודות של CRUD
public class JobService {
    private static final Logger logger = LoggerFactory.getLogger(JobService.class); // Logger instance
    private final JobRepository jobRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Constructor-based Dependency Injection (DI)
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // 1 - Retrieve all jobs
    public List<Job> getAllJobs() {
        logger.info("Retrieving all jobs from the database.");
        return jobRepository.findAll();
    }

    // 2 - Create a new job
    public Job createJob(String jobName, String status, LocalDateTime createdAt, LocalDateTime updatedAt, String jobType, String password) {
        logger.info("Creating new job: {}", jobName);
        Job job = new Job(jobName, status, createdAt, updatedAt, jobType, password);
        Job savedJob = jobRepository.save(job);
        logger.info("Job created successfully with ID: {}", savedJob.getId());
        return savedJob;
    }

    // 3 - Retrieve a job by ID
    public Optional<Job> getJobById(Long id) {
        logger.info("Retrieving job with ID: {}", id);
        return jobRepository.findById(id);
    }

    // 4 - Update an existing job
    public Optional<Job> updateJob(Long id, String jobName, String status, LocalDateTime updatedAt, String jobType, String password) {
        logger.info("Updating job with ID: {}", id);
        return Optional.ofNullable(jobRepository.findById(id).map(existingJob -> {
            existingJob.setJobName(jobName);
            existingJob.setStatus(status);
            existingJob.setUpdatedAt(updatedAt);
            existingJob.setJobType(jobType);
            if (password != null && !password.isEmpty()) {
                existingJob.setPassword(passwordEncoder.encode(password));
            }
            Job updatedJob = jobRepository.save(existingJob);
            logger.info("Job updated successfully with ID: {}", updatedJob.getId());
            return updatedJob;
        }).orElseThrow(() -> {
            logger.error("Job not found with ID: {}", id);
            return new RuntimeException("Job not found");
        }));
    }

    // 5 - Delete a job by ID
    public void deleteJob(Long id) {
        logger.info("Deleting job with ID: {}", id);
        Job job = jobRepository.findById(id).orElseThrow(() -> {
            logger.error("Job not found with ID: {}", id);
            return new RuntimeException("Job not found");
        });
        jobRepository.delete(job);
        logger.info("Job deleted successfully with ID: {}", id);
    }

    // 6 - Find jobs by status
    public List<Job> findJobsByStatus(String status) {
        logger.info("Finding jobs with status: {}", status);
        return jobRepository.findByStatus(status);
    }

    // 7 - Find jobs by job type
    public List<Job> findJobsByJobType(String jobType) {
        logger.info("Finding jobs with job type: {}", jobType);
        return jobRepository.findByJobType(jobType);
    }

    // 8 - Find jobs by date range
    public List<Job> findJobsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        logger.info("Finding jobs between {} and {}", startDate, endDate);
        return jobRepository.findByDateRange(startDate, endDate);
    }
}