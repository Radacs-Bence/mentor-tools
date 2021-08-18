package mentortools.registrations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.students.Student;
import mentortools.trainingclasses.TrainingClass;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private TrainingClass trainingClass;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus registrationStatus;

    public Registration(Student student, TrainingClass trainingClass, RegistrationStatus registrationStatus) {
        this.student = student;
        this.trainingClass = trainingClass;
        this.registrationStatus = registrationStatus;
    }
}
