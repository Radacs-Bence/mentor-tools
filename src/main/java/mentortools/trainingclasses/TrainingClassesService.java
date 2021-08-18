package mentortools.trainingclasses;

import mentortools.registrations.CreateRegistrationCommand;
import mentortools.registrations.Registration;
import mentortools.registrations.RegistrationStatus;
import mentortools.students.Student;
import mentortools.students.StudentsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingClassesService {

    private ModelMapper modelMapper;
    private TrainingClassesRepository trainingClassesRepository;
    private StudentsRepository studentsRepository;

    public TrainingClassesService(ModelMapper modelMapper, TrainingClassesRepository trainingClassesRepository, StudentsRepository studentsRepository) {
        this.modelMapper = modelMapper;
        this.trainingClassesRepository = trainingClassesRepository;
        this.studentsRepository = studentsRepository;
    }

    public List<TrainingClassDTO> listAllClasses(Optional<String> name) {
        List<TrainingClass> trainingClasses = trainingClassesRepository.findAll();
        List<TrainingClass> filtered = trainingClasses.stream()
                .filter(trainingClass -> name.isEmpty() || trainingClass.getName().toLowerCase().contains(name.get().toLowerCase()))
                .collect(Collectors.toList());
        Type targetListType = new TypeToken<List<TrainingClassDTO>>() {
        }.getType();
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

    @Transactional
    public TrainingClassDTO modifyTrainingAttributes(Long id, UpdateTrainingClassCommand command) {
        TrainingClass trainingClass = searchById(id);
        trainingClass.setName(command.getName());
        trainingClass.setStart(command.getStart());
        trainingClass.setEnd(command.getEnd());

        return modelMapper.map(trainingClass, TrainingClassDTO.class);
    }

    public void deleteById(Long id) {
        TrainingClass trainingClass = searchById(id);
        trainingClassesRepository.deleteById(id);
    }

    @Transactional
    public TrainingClassWithRegistrationsDTO registerStudent(Long id, CreateRegistrationCommand command) {
        TrainingClass trainingClass = searchById(id);
        Student student = studentsRepository.findById(command.getStudentId()).orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));

        Registration registration = new Registration(student, trainingClass, RegistrationStatus.ACTIVE);

        trainingClass.addRegistration(registration);
        student.addRegistration(registration);

        return modelMapper.map(trainingClass, TrainingClassWithRegistrationsDTO.class);
    }

    public TrainingClassWithRegistrationsDTO findWithRegistrations(Long id) {
        TrainingClass trainingClass = searchById(id);
        return modelMapper.map(trainingClass, TrainingClassWithRegistrationsDTO.class);
    }
}
