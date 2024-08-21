package hit.final_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

// אני רוצה שהמחלקה הזאת תופעל בזמן ריצה רק לאחר האפליקציה רצה והדאטה בייס מחובר
// אתחול הדאטה בייס
// call inversion of control

//מאשפר למטודות לרוץ בזמן ריצה וגם מאתחל את הטבלה
@Configuration // Allows methods to run at runtime and initializes the table
public class DatabaseSeeder {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Encrypts passwords

    // הערך המוחזר מהמטודה שמנוהל ע״ הסביבה
    @Bean // The value returned from the method is managed by the Spring context
    CommandLineRunner initDatabase(JobRepository jobRepository){
        return args -> {
            logger.info("Initializing database at runtime...");

            // Create jobs with initial data
            Job job1 = new Job("Build Project", "In Progress", LocalDateTime.now(), LocalDateTime.now(), "Build", passwordEncoder.encode("password1"));
            Job job2 = new Job("Deploy Project", "Pending", LocalDateTime.now(), LocalDateTime.now(), "Deploy", passwordEncoder.encode("password2"));
            Job job3 = new Job("Test Project", "Completed", LocalDateTime.now(), LocalDateTime.now(), "Test", passwordEncoder.encode("password3"));

            // Save the jobs to the database
            jobRepository.save(job1);
            logger.info("Created Job: {}", job1);
            jobRepository.save(job2);
            logger.info("Created Job: {}", job2);
            jobRepository.save(job3);
            logger.info("Created Job: {}", job3);

            logger.info("Database seeding completed");
        };
    }

}
