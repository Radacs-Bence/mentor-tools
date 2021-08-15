package mentortools.trainingclasses;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingClassesService {

    private ModelMapper modelMapper;
    private TrainingClassesRepository trainingClassesRepository;

    public TrainingClassesService(ModelMapper modelMapper, TrainingClassesRepository trainingClassesRepository) {
        this.modelMapper = modelMapper;
        this.trainingClassesRepository = trainingClassesRepository;
    }

    public List<TrainingClassDTO> listAllClasses(Optional<String> name) {
        List<TrainingClass> trainingClasses = trainingClassesRepository.findAll();
        List<TrainingClass> filtered = trainingClasses.stream()
                .filter(trainingClass -> name.isEmpty() || trainingClass.getName().toLowerCase().contains(name.get().toLowerCase()))
                .collect(Collectors.toList());
        Type targetListType = new TypeToken<List<TrainingClassDTO>>(){}.getType();
        return modelMapper.map(filtered, targetListType);
    }

    public TrainingClassDTO findById(Long id) {
        TrainingClass trainingClass = searchById(id);
        return modelMapper.map(trainingClass, TrainingClassDTO.class);
    }

    private TrainingClass searchById(Long id) {
        return trainingClassesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("TrainingClass not found: " + id));
    }

    public void deleteAll() {
        trainingClassesRepository.deleteAll();
    }

    public TrainingClassDTO saveTrainingClass(CreateTrainingClassCommand command) {
        TrainingClass trainingClass = trainingClassesRepository.save(new TrainingClass(command.getName(), command.getStart(), command.getEnd()));

        return modelMapper.map(trainingClass, TrainingClassDTO.class);
    }
}
