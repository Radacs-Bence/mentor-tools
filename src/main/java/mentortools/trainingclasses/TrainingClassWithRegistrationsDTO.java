package mentortools.trainingclasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.registrations.RegistrationWithStudentDTO;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingClassWithRegistrationsDTO {

    private Long id;

    private String name;

    private LocalDate start;

    private LocalDate end;

    private Set<RegistrationWithStudentDTO> registrations;

}
