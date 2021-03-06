package mentortools.trainingclasses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mentortools.registrations.CreateRegistrationCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainingclasses")
@Tag( name = "Operations on Classes")
public class TrainingClassesController {

    private TrainingClassesService trainingClassesService;

    public TrainingClassesController(TrainingClassesService trainingClassesService) {
        this.trainingClassesService = trainingClassesService;
    }

    @GetMapping
    @Operation(summary = "Find all classes",
            description = "Finds all classes in repository.")
    public List<TrainingClassDTO> listAllClasses(@RequestParam Optional<String> name){
        return trainingClassesService.listAllClasses(name);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a class by id",
            description = "Finds a class in the repository.")
    public TrainingClassDTO findById(@PathVariable Long id){
        return trainingClassesService.findById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes all classes",
            description = "Removes every class from repository!")
    public void deleteAll(){
        trainingClassesService.deleteAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a class",
            description = "Creates a class, with name, start time, and end time.")
    public TrainingClassDTO saveTrainingClass(@RequestBody @Valid CreateTrainingClassCommand command){
        return trainingClassesService.saveTrainingClass(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Modify a class",
            description = "Modifies a class's name, start time, and end time.")
    public TrainingClassDTO modifyTrainingAttributes(@PathVariable Long id, @RequestBody @Valid UpdateTrainingClassCommand command){
        return trainingClassesService.modifyTrainingAttributes(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a class",
            description = "Removes a class from the repository!")
    public void deleteById(@PathVariable Long id){
        trainingClassesService.deleteById(id);
    }

    @PostMapping("/{id}/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registers a student for the class",
            description = "Creates a Registration belonging to the class and a student given with id.")
    public TrainingClassWithRegistrationsDTO registerStudent(@PathVariable Long id, @RequestBody @Valid CreateRegistrationCommand command){
        return trainingClassesService.registerStudent(id, command);
    }

    @GetMapping("/{id}/registrations")
    @Operation(summary = "Find a class by id, with registrations",
            description = "Finds a class in the repository, with registrations.")
    public TrainingClassWithRegistrationsDTO findWithRegistrations(@PathVariable Long id){
        return trainingClassesService.findWithRegistrations(id);
    }
}
