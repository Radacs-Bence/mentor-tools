package mentortools.students;


import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentsService {

    private ModelMapper modelMapper;
    private StudentsRepository studentsRepository;

    public StudentsService(ModelMapper modelMapper, StudentsRepository studentsRepository) {
        this.modelMapper = modelMapper;
        this.studentsRepository = studentsRepository;
    }

    public List<StudentDTO> listAllStudents(Optional<String> name) {
        List<Student> students = studentsRepository.findAll();
        List<Student> filtered = students.stream()
                .filter(student -> name.isEmpty() || student.getName().toLowerCase().contains(name.get().toLowerCase()))
                .collect(Collectors.toList());
        Type targetListType = new TypeToken<List<StudentDTO>>() {
        }.getType();
        return modelMapper.map(filtered, targetListType);
    }

    public StudentDTO findById(Long id) {
        Student student = searchById(id);
        return modelMapper.map(student, StudentDTO.class);
    }

    private Student searchById(Long id) {
        return studentsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }

    public void deleteAll() {
        studentsRepository.deleteAll();
    }

    public StudentDTO saveStudent(CreateStudentCommand command) {
        Student student = studentsRepository.save(new Student(command.getName(), command.getEMail(), command.getGitHub(), command.getGitHub()));

        return modelMapper.map(student, StudentDTO.class);
    }

    @Transactional
    public StudentDTO modifyStudentAttributes(Long id, UpdateStudentCommand command) {
        Student student = searchById(id);
        student.setName(command.getName());
        student.setEMail(command.getEMail());
        student.setGitHub(command.getGitHub());
        student.setComment(command.getComment());

        return modelMapper.map(student, StudentDTO.class);
    }

    public void deleteById(Long id) {
        Student student = searchById(id);
        studentsRepository.deleteById(id);
    }

}
