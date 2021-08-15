package mentortools.trainingclasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTrainingClassCommand {

    @NotBlank
    private String name;

    private LocalDate start;

    private LocalDate end;
}
