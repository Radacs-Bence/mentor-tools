package mentortools.registrations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.students.StudentSimpleDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationWithStudentDTO {

    private Long id;

    private StudentSimpleDTO student;

    private RegistrationStatus registrationStatus;


}
