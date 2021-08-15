package mentortools.trainingclasses;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainingClassesControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    TrainingClassesService trainingClassesService;

    @AfterEach
    void init() {
        trainingClassesService.deleteAll();
    }


    @Test
    void listAllClasses() {
        TrainingClassDTO trainingClassFirst = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand("Java", LocalDate.of(2000, 1, 1), LocalDate.MAX),
                TrainingClassDTO.class);

        TrainingClassDTO trainingClassSecond = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand("JavaScript", LocalDate.of(2000, 2, 2), LocalDate.MAX),
                TrainingClassDTO.class);

        List<TrainingClassDTO> trainingClasses = template.exchange("/api/trainingclasses/",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TrainingClassDTO>>() {
                        })
                .getBody();

        assertThat(trainingClasses)
                .hasSize(2)
                .extracting(TrainingClassDTO::getName)
                .containsExactly("Java", "JavaScript");

    }

    @Test
    void findById() {
        TrainingClassDTO trainingClassCreated = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand("Java", LocalDate.of(2000, 1, 1), LocalDate.MAX),
                TrainingClassDTO.class);

        TrainingClassDTO trainingClassFound = template.exchange("/api/trainingclasses/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<TrainingClassDTO>() {
                        },
                        trainingClassCreated.getId())
                .getBody();

        assertThat(trainingClassFound.getName()).isEqualTo("Java");
    }

}