package hit.final_project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
// (DTO) Data transfer Object
public class JobDTO {

    private Long id;
    private String jobName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String jobType;
    private String password;

}