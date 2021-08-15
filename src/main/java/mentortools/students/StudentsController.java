package mentortools.students;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@Tag( name = "Operations on students")
public class StudentsController {

    private StudentsService studentsService;

    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping
    @Operation(summary = "Find all students",
            description = "Finds all students in repository.")
    public List<StudentDTO> listAllStudents(@RequestParam Optional<String> name){
        return studentsService.listAllStudents(name);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find students by id",
            description = "Finds a student in the repository.")
    public StudentDTO findById(@PathVariable Long id){
        return studentsService.findById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes all students",
            description = "Removes every student from repository!")
    public void deleteAll(){
        studentsService.deleteAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a student",
            description = "Creates a student, with name, start time, and end time.")
    public StudentDTO saveStudent(@RequestBody @Valid CreateStudentCommand command){
        return studentsService.saveStudent(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Modify a student",
            description = "Modifies a student's name, start time, and end time.")
    public StudentDTO modifyStudentAttributes(@PathVariable Long id, @RequestBody @Valid UpdateStudentCommand command){
        return studentsService.modifyStudentAttributes(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a student",
            description = "Removes a student from the repository!")
    public void deleteById(@PathVariable Long id){
        studentsService.deleteById(id);
    }
}
