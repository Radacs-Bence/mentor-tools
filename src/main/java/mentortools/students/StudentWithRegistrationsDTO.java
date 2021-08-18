package mentortools.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.registrations.RegistrationWithTrainingClassDTO;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithRegistrationsDTO {

    private Long id;

    private String name;

    private String eMail;

    private String gitHub;

    private String comment;

    private Set<RegistrationWithTrainingClassDTO> registrations;

}
