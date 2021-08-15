package mentortools.students;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentsControllerRestTemplateIT {


    @Autowired
    TestRestTemplate template;

    @Autowired
    StudentsService studentsService;

    @AfterEach
    void init() {
        studentsService.deleteAll();
    }


    @Test
    void listAllStudentsTest() {
        StudentDTO studentFirst = template.postForObject("/api/students",
                new CreateStudentCommand("Gibsz Jakab", "gjakab@examplemail.com", "https://github.com/Gipsz-Jakab-exampleaddress", ""),
                StudentDTO.class);

        StudentDTO studentSecond = template.postForObject("/api/students",
                new CreateStudentCommand("Kis Piroska", "picipiri@examplemail.com", "https://github.com/picipiri-exampleaddress", ""),
                StudentDTO.class);

        List<StudentDTO> students = template.exchange("/api/students/",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<StudentDTO>>() {
                        })
                .getBody();

        assertThat(students)
                .hasSize(2)
                .extracting(StudentDTO::getName)
                .containsExactly("Gibsz Jakab", "Kis Piroska");

    }

    @Test
    void findByIdTest() {
        StudentDTO studentCreated = template.postForObject("/api/students",
                new CreateStudentCommand("Gibsz Jakab", "gjakab@examplemail.com", "https://github.com/Gipsz-Jakab-exampleaddress", ""),
                StudentDTO.class);

        StudentDTO studentFound = template.exchange("/api/students/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StudentDTO>() {
                        },
                        studentCreated.getId())
                .getBody();

        assertThat(studentFound.getName()).isEqualTo("Gibsz Jakab");
    }

    @Test
    void modifyTrainingAttributesTest() {
        StudentDTO studentCreated = template.postForObject("/api/students",
                new CreateStudentCommand("Gibsz Jakab", "gjakab@examplemail.com", "https://github.com/Gipsz-Jakab-exampleaddress", ""),
                StudentDTO.class);

        template.put("/api/students/{id}",
                new CreateStudentCommand("Gibsz Jónás", "gjonas@examplemail.com", "https://github.com/Gipsz-Jonas", ""),
                studentCreated.getId());

        StudentDTO studentFound = template.exchange("/api/students/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<StudentDTO>() {
                        },
                        studentCreated.getId())
                .getBody();

        assertThat(studentFound.getName()).isEqualTo("Gibsz Jónás");
        assertThat(studentFound.getEMail()).isEqualTo("gjonas@examplemail.com");
    }

    @Test
    void deleteByIdTest() {
        StudentDTO studentFirst = template.postForObject("/api/students",
                new CreateStudentCommand("Gibsz Jakab", "gjakab@examplemail.com", "https://github.com/Gipsz-Jakab-exampleaddress", ""),
                StudentDTO.class);

        StudentDTO studentSecond = template.postForObject("/api/students",
                new CreateStudentCommand("Kis Piroska", "picipiri@examplemail.com", "https://github.com/picipiri-exampleaddress", ""),
                StudentDTO.class);

        StudentDTO studentThird = template.postForObject("/api/students",
                new CreateStudentCommand("Gibsz Hiba", "hiba@hibamail.com", "https://github.com/Gipsz-Hiba-exampleaddress", ""),
                StudentDTO.class);

        template.delete("/api/students/{id}", studentThird.getId());

        List<StudentDTO> students = template.exchange("/api/students/",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<StudentDTO>>() {
                        })
                .getBody();

        assertThat(students)
                .hasSize(2)
                .extracting(StudentDTO::getName)
                .containsExactly("Gibsz Jakab", "Kis Piroska");
    }

}