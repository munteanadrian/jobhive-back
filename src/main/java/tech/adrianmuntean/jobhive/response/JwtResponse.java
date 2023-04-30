package tech.adrianmuntean.jobhive.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JwtResponse {
    private String type = "Bearer";
    private String token;
    private Long id;
    private String email;

    public JwtResponse(String accessToken, Long id, String email) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
    }
}
