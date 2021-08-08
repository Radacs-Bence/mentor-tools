package mentortools;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
