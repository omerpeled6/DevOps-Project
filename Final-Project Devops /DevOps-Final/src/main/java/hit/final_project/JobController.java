package hit.final_project;

// Rest Controller for Job Entity
// this controller handle Http req and res for job interaction and operations
//  מאפשר ליוזרים לתקשר מול הסרוויס שמקשר מול הדאטה בייס

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// // הזרקת המדוטות למטה לתוך הגוף של הרספונס
@RestController // Marks this class as a REST controller
@RequestMapping("/api/jobs") // Sets the base URL path for this controller

public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class); // Logger instance
    private final JobService jobService;

    // Constructor-based Dependency Injection (DI)
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // 1 - Retrieve all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        logger.info("Received request to retrieve all jobs.");
        return jobService.getAllJobs();
    }

    // 2 - Create a new job
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        logger.info("Received request to create a new job.");
        Job createdJob = jobService.createJob(
                job.getJobName(),
                job.getStatus(),
                job.getCreatedAt() != null ? job.getCreatedAt() : LocalDateTime.now(),
                job.getUpdatedAt() != null ? job.getUpdatedAt() : LocalDateTime.now(),
                job.getJobType(),
                job.getPassword()
        );
        logger.info("Job created successfully with ID: {}", createdJob.getId());
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    // 3 - Retrieve a job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        logger.info("Received request to retrieve job with ID: {}", id);
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.error("Job not found with ID: {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    // 4 - Update a job by ID
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Long id,
            @RequestBody Job job
    ) {
        logger.info("Received request to update job with ID: {}", id);
        Optional<Job> updatedJob = jobService.updateJob(
                id,
                job.getJobName(),
                job.getStatus(),
                LocalDateTime.now(), // Set updatedAt to current time
                job.getJobType(),
                job.getPassword()
        );
        return updatedJob.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.error("Job not found for update with ID: {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    // 5 - Delete a job by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        logger.info("Received request to delete job with ID: {}", id);
        try {
            jobService.deleteJob(id);
            logger.info("Job deleted successfully with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("Error deleting job with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 6 - Retrieve jobs by status
    @GetMapping("/status/{status}")
    public List<Job> getJobsByStatus(@PathVariable String status) {
        logger.info("Received request to retrieve jobs by status: {}", status);
        return jobService.findJobsByStatus(status);
    }

    // 7 - Retrieve jobs by job type
    @GetMapping("/jobType/{jobType}")
    public List<Job> getJobsByJobType(@PathVariable String jobType) {
        logger.info("Received request to retrieve jobs by job type: {}", jobType);
        return jobService.findJobsByJobType(jobType);
    }

    // 8 - Retrieve jobs by a date range
    @GetMapping("/date-range")
    public List<Job> getJobsByDateRange(
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate
    ) {
        logger.info("Received request to retrieve jobs by date range: {} - {}", startDate, endDate);
        return jobService.findJobsByDateRange(startDate, endDate);
    }
}