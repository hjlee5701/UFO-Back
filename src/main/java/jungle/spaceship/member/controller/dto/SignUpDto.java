package jungle.spaceship.member.controller.dto;

import jungle.spaceship.member.entity.family.FamilyRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SignUpDto {

    @NotBlank(message = "Nickname is required")
    private String nickname;

    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate must be in the past")
    private LocalDate birthdate;

    private FamilyRole familyRole;

}