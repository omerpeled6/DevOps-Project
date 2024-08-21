package hit.final_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
מבחני אינטגרציה נועדו לבדוק כיצד רכיבים או מודולים שונים של אפליקציה פועלים יחד כמכלול.
 שלא כמו מבחני יחידה, המתמקדים ביחידות קוד בודדות (כמו שיטות או מחלקות),
  מבחני אינטגרציה מתמקדים באימות האינטראקציות בין חלקים שונים של המערכת.
   ביישום אינטרנט, מבחני אינטגרציה עשויים לבדוק כיצד שכבות הבקר,
   השירות והמאגר מתקשרים זה עם זה ועם מסד הנתונים בפועל.
 */

// Note: This code was created with the help of AI assistance to ensure best practices and thorough testing.
// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Integration Test @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@SpringBootTest // Tells Spring Boot to start the application context for testing.
@AutoConfigureMockMvc // Configures MockMvc, which is used to simulate HTTP requests and responses.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// Ensures the application context and database are reset after each test method,
// preventing state from one test from affecting another.
public class JobControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    // MockMvc is used to perform HTTP requests in tests.

    @Autowired
    private WebApplicationContext webApplicationContext;
    // WebApplicationContext is the Spring context used for web applications,
    // which holds all the beans and components.

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        // Sets up MockMvc using the web application context, allowing it to interact with the
        // entire application stack, including controllers, services, and repositories.
    }

    @Test
    public void testCreateJob() throws Exception {
        String jobJson = "{ \"jobName\": \"Integration Test Job\", \"status\": \"In Progress\", \"jobType\": \"Build\", \"password\": \"password123\" }";
        // JSON string representing the new job to be created.

        mockMvc.perform(post("/api/jobs") // Sends a POST request to the /api/jobs endpoint.
                        .contentType(MediaType.APPLICATION_JSON) // Specifies that the content type is JSON.
                        .content(jobJson)) // The body of the POST request, which is the job JSON.
                .andExpect(status().isCreated()) // Expects that the response status is 201 Created.
                .andExpect(jsonPath("$.jobName").value("Integration Test Job")) // Expects that the jobName field in the response is "Integration Test Job".
                .andExpect(jsonPath("$.status").value("In Progress")); // Expects that the status field in the response is "In Progress".
    }

    @Test
    public void testGetAllJobs() throws Exception {
        mockMvc.perform(get("/api/jobs")) // Sends a GET request to the /api/jobs endpoint.
                .andExpect(status().isOk()) // Expects that the response status is 200 OK.
                .andExpect(jsonPath("$").isArray()); // Expects that the response is an array.
    }


    @Test
    public void testUpdateJob() throws Exception {
        // First, create a job
        String jobJson = "{ \"jobName\": \"Integration Test Job\", \"status\": \"In Progress\", \"jobType\": \"Build\", \"password\": \"password123\" }";

        mockMvc.perform(post("/api/jobs") // Sends a POST request to create a job.
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobJson))
                .andExpect(status().isCreated());

        // Then, update it
        String updatedJobJson = "{ \"jobName\": \"Updated Job\", \"status\": \"Completed\", \"jobType\": \"Deploy\", \"password\": \"newpassword123\" }";

        mockMvc.perform(put("/api/jobs/1") // Sends a PUT request to update the job with ID 1.
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJobJson))
                .andExpect(status().isOk()) // Expects that the response status is 200 OK.
                .andExpect(jsonPath("$.jobName").value("Updated Job")) // Expects that the jobName field in the response is "Updated Job".
                .andExpect(jsonPath("$.status").value("Completed")); // Expects that the status field in the response is "Completed".
    }

    @Test
    public void testDeleteJob() throws Exception {
        // First, create a job
        String jobJson = "{ \"jobName\": \"Integration Test Job\", \"status\": \"In Progress\", \"jobType\": \"Build\", \"password\": \"password123\" }";

        mockMvc.perform(post("/api/jobs") // Sends a POST request to create a job.
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobJson))
                .andExpect(status().isCreated());

        // Then, delete it
        mockMvc.perform(delete("/api/jobs/1")) // Sends a DELETE request to delete the job with ID 1.
                .andExpect(status().isNoContent()); // Expects that the response status is 204 No Content.

        // Verify it's deleted
        mockMvc.perform(get("/api/jobs/1")) // Sends a GET request to retrieve the job with ID 1.
                .andExpect(status().isNotFound()); // Expects that the response status is 404 Not Found.
    }
}