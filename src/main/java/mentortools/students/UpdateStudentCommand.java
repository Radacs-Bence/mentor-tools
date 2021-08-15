package mentortools.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentCommand {

    private String name;

    private String eMail;

    private String gitHub;

    private String comment;
}
