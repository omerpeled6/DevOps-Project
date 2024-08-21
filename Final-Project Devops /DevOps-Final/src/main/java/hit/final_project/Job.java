package hit.final_project;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity // Save to SQL Table
@Table(name = "app_job") // Table Name
@Data // Auto-generates getters, setters, toString, etc.
@NoArgsConstructor // Default constructor
public class Job {

    // Password encoder
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String jobName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String jobType;
    private String password;

    // Constructor without password
    public Job(String jobName, String status, LocalDateTime createdAt, LocalDateTime updatedAt, String jobType) {
        this(jobName, status, createdAt, updatedAt, jobType, UUID.randomUUID().toString());
    }

    // Constructor with all fields
    public Job(String jobName, String status, LocalDateTime createdAt, LocalDateTime updatedAt, String jobType, String password) {
        this.jobName = jobName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.jobType = jobType;
        this.password = passwordEncoder.encode(password);
    }
}