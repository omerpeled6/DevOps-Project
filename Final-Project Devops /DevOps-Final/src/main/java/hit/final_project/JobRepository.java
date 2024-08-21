package hit.final_project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

// שכבה מקשרת לדאטה בייס sql רלציוני בעזרת גישה לאיטרפייס אחר jpaRepository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Find jobs by status
    List<Job> findByStatus(String status);

    // Find jobs by job type
    List<Job> findByJobType(String jobType);

    // Find jobs by status and job type
    List<Job> findByStatusAndJobType(String status, String jobType);

    // Find jobs by date range
    @Query("SELECT j FROM Job j WHERE j.createdAt BETWEEN :startDate AND :endDate")
    List<Job> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}