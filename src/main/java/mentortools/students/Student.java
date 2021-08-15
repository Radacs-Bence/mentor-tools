package mentortools.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public Student(String name, String eMail, String gitHub, String comment) {
        this.name = name;
        this.eMail = eMail;
        this.gitHub = gitHub;
        this.comment = comment;
    }
}
