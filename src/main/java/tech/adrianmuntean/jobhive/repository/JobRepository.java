package tech.adrianmuntean.jobhive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.adrianmuntean.jobhive.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}
