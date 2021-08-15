package mentortools.trainingclasses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(summary = "Find classes by id",
            description = "Finds a class in the repository.")
    public TrainingClassDTO findById(@PathVariable Long id){
        return trainingClassesService.findById(id);
    }

    @DeleteMapping
    @Operation(summary = "Deletes all classes",
            description = "Removes every class from repository!")
    public void deleteAll(){
        trainingClassesService.deleteAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a course",
            description = "Creates a course, with name and language.")
    public TrainingClassDTO saveTrainingClass(@RequestBody @Valid CreateTrainingClassCommand command){
        return trainingClassesService.saveTrainingClass(command);
    }
}