package mentortools.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.registrations.Registration;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String eMail;

    private String gitHub;

    private String comment;

    @OneToMany(mappedBy = "student")
    private Set<Registration> registrations;

    public Student(String name, String eMail, String gitHub, String comment) {
        this.name = name;
        this.eMail = eMail;
        this.gitHub = gitHub;
        this.comment = comment;
    }

    public void addRegistration(Registration registration){
        if (registrations == null) {
            registrations = new HashSet<>();
        }
        registrations.add(registration);
    }

}
