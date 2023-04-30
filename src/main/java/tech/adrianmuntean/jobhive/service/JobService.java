package tech.adrianmuntean.jobhive.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tech.adrianmuntean.jobhive.DTO.JobDTO;
import tech.adrianmuntean.jobhive.model.Job;
import tech.adrianmuntean.jobhive.model.STATUS;
import tech.adrianmuntean.jobhive.repository.JobRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    //region CREATE OPERATIONS

    /**
     * Create a new job
     *
     * @param jobDTO the job to be created
     * @return the created job
     */
    public Job createJob(JobDTO jobDTO) {
        Job job = new Job();
        job.setDTO(jobDTO);
        return jobRepository.save(job);
    }

    //endregion

    //region READ OPERATIONS

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }
    //endregion

    //region UPDATE OPERATIONS

    public Job updateJob(Long id, String key, String value) {
        Optional<Job> optJob = jobRepository.findById(id);

        if (optJob.isPresent()) {
            Job job = optJob.get();

            switch (key) {
                case "company" -> {
                    job.setCompany(value);
                }
                case "status" -> {
                    job.setStatus(STATUS.valueOf(value));
                }
//                Location
                case "city" -> {
                    job.setCity(value);
                }
                case "country" -> {
                    job.setCountry(value);
                }
                case "remote" -> {
                    job.setRemote(!job.isRemote());
                }
//                Dates
                case "applied" -> {
                    job.setApplied(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                }
            }

            job.setLastActivity(LocalDate.now());
            return jobRepository.save(job);
        } else {
            throw new EntityNotFoundException("No job with ID " + id);
        }
    }

    //endregion

    //region DELETE OPERATIONS

    /**
     * Delete a job
     *
     * @param id the id of the job
     */
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
    
    //endregion
}
