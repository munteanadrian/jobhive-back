package tech.adrianmuntean.jobhive.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.adrianmuntean.jobhive.DTO.JobDTO;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;


    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "status", nullable = false)
    private STATUS status;


    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "country", nullable = true)
    private String country;

    @Column(name = "remote", nullable = true)
    private boolean remote;


    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "applied", nullable = true)
    private LocalDate applied;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "last_activity", nullable = false)
    private LocalDate lastActivity;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;


    /**
     * Set the job's fields from a JobDTO
     */
    public void setDTO(JobDTO jobDTO) {
        this.company = jobDTO.getCompany();
        this.city = jobDTO.getCity();
        this.country = jobDTO.getCountry();
        this.remote = jobDTO.isRemote();
        this.status = jobDTO.isApplied() ? STATUS.APPLIED : STATUS.NEW;

        this.setApplied(LocalDate.now());
        this.setLastActivity(LocalDate.now());
    }
}
