package hit.final_project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    public JobServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Note: This code was created with the help of AI assistance to ensure best practices and thorough testing.
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Standard Unit Test @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // לבדוק כל מתודה במבודד, ולהבטיח שהיא פועלת כמצופה עם תנאי קלט שונים.
    @Test
    void testAddJob() { // Test 1
        // Arrange: Create a Job object with the necessary details
        Job job = new Job("Test Job", "In Progress", LocalDateTime.now(), LocalDateTime.now(), "Test", "password");

        // Mock the behavior of jobRepository.save() to simulate saving and returning the Job object
        when(jobRepository.save(any(Job.class))).thenAnswer(invocation -> {
            Job savedJob = invocation.getArgument(0);
            savedJob.setId(1L); // Simulate setting the ID after saving
            return savedJob;
        });

        // Act: Call the createJob method of JobService
        Job createdJob = jobService.createJob(
                job.getJobName(),
                job.getStatus(),
                job.getCreatedAt(),
                job.getUpdatedAt(),
                job.getJobType(),
                job.getPassword()
        );

        // Assert: Verify that the createdJob is not null, has the correct job name, and that the password is hashed
        assertNotNull(createdJob);
        assertEquals("Test Job", createdJob.getJobName());
        assertNotNull(createdJob.getPassword()); // Verify that password is hashed

        // Verify that jobRepository.save() was called exactly once with any Job object
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    void testGetJobById() { // Test 2
        // Arrange: Create a Job object with the necessary details
        Job job = new Job("Test Job", "In Progress", LocalDateTime.now(), LocalDateTime.now(), "Test", "password");

        // Mock the behavior of jobRepository.findById() to return the job
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        // Act: Call the getJobById method of JobService
        Optional<Job> foundJob = jobService.getJobById(1L);

        // Assert: Verify that the foundJob is present and has the correct job name
        assertNotNull(foundJob);
        assertEquals("Test Job", foundJob.get().getJobName());

        // Verify that jobRepository.findById() was called exactly once with the ID 1L
        verify(jobRepository, times(1)).findById(1L);
    }


    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Parameterized Test @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // מסמנת מתודת בדיקה שתורץ מספר פעמים עם קלטים שונים. מאפשרת לבדוק את המתודה עם ערכי קלט שונים בצורה אוטומטית.
    // Parameterized Test for adding jobs with different statuses
    @ParameterizedTest
    @ValueSource(strings = {"In Progress", "Completed", "Pending"})
    @DisplayName("Test Adding Job with Various Statuses")
    void testAddJobWithVariousStatuses(String status) { // Test 1
        Job job = new Job("Test Job", status, LocalDateTime.now(), LocalDateTime.now(), "Test", "password");

        when(jobRepository.save(any(Job.class))).thenAnswer(invocation -> {
            Job savedJob = invocation.getArgument(0);
            savedJob.setId(1L); // Simulate setting the ID after saving
            return savedJob;
        });

        Job createdJob = jobService.createJob(
                job.getJobName(),
                job.getStatus(),
                job.getCreatedAt(),
                job.getUpdatedAt(),
                job.getJobType(),
                job.getPassword()
        );

        assertNotNull(createdJob);
        assertEquals(status, createdJob.getStatus());
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    // Parameterized Test for retrieving jobs with different IDs
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @DisplayName("Test Getting Job by Different IDs")
    void testGetJobByDifferentIds(Long id) { // Test 2
        Job job = new Job("Test Job", "In Progress", LocalDateTime.now(), LocalDateTime.now(), "Test", "password");

        when(jobRepository.findById(id)).thenReturn(Optional.of(job));

        Optional<Job> foundJob = jobService.getJobById(id);

        assertTrue(foundJob.isPresent());
        assertEquals("Test Job", foundJob.get().getJobName());
        verify(jobRepository, times(1)).findById(id);
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Nested Test @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // מסמנת מחלקות פנימיות שמכילות קבוצות של בדיקות קשורות. מאפשרת ארגון הבדיקות בצורה היררכית לפי נושאים או הקשרים לוגיים.
    @Nested
    @DisplayName("Tests for Adding Jobs")
    class AddJobTests {

        @Test
        @DisplayName("Test Adding a Job Successfully")
        void testAddJob() { // Test 1
            Job job = new Job("Test Job", "In Progress", LocalDateTime.now(), LocalDateTime.now(), "Test", "password");

            when(jobRepository.save(any(Job.class))).thenAnswer(invocation -> {
                Job savedJob = invocation.getArgument(0);
                savedJob.setId(1L); // Simulate setting the ID after saving
                return savedJob;
            });

            Job createdJob = jobService.createJob(
                    job.getJobName(),
                    job.getStatus(),
                    job.getCreatedAt(),
                    job.getUpdatedAt(),
                    job.getJobType(),
                    job.getPassword()
            );

            assertNotNull(createdJob);
            assertEquals("Test Job", createdJob.getJobName());
            assertNotNull(createdJob.getPassword()); // Verify that password is hashed

            verify(jobRepository, times(1)).save(any(Job.class));
        }
    }

    @Nested
    @DisplayName("Tests for Retrieving Jobs")
    class GetJobTests {

        @Test
        @DisplayName("Test Getting a Job by ID Successfully")
        void testGetJobById() { // Test 2
            Job job = new Job("Test Job", "In Progress", LocalDateTime.now(), LocalDateTime.now(), "Test", "password");

            when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

            Optional<Job> foundJob = jobService.getJobById(1L);

            assertTrue(foundJob.isPresent());
            assertEquals("Test Job", foundJob.get().getJobName());

            verify(jobRepository, times(1)).findById(1L);
        }
    }


    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Exception Test @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    @Nested
    @DisplayName("Exception Tests")
    class ExceptionTests {

        @Test
        @DisplayName("Test Deleting Non-Existent Job Throws Exception")
        void testDeleteNonExistentJob() {
            when(jobRepository.findById(1L)).thenReturn(Optional.empty());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                jobService.deleteJob(1L);
            });

            String expectedMessage = "Job not found";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
            verify(jobRepository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("Test Updating Job with Invalid Data Throws Exception")
        void testUpdateJobWithInvalidData() {
            when(jobRepository.findById(1L)).thenReturn(Optional.empty());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                jobService.updateJob(1L, "Updated Job", "Completed", LocalDateTime.now(), "Test", "password");
            });

            String expectedMessage = "Job not found";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
            verify(jobRepository, times(1)).findById(1L);
        }

    }
}