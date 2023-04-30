package tech.adrianmuntean.jobhive.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobDTO {
    private String company;
    private boolean applied;

    //    location
    private String city;
    private String country;
    private boolean remote;
}
