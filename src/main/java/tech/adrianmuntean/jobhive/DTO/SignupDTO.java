package tech.adrianmuntean.jobhive.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.adrianmuntean.jobhive.model.ROLE;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    private String email;
    private String password;
    private String name;
    private ROLE role = ROLE.USER;
}
