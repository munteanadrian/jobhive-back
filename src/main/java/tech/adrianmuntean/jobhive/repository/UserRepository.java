package tech.adrianmuntean.jobhive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.adrianmuntean.jobhive.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
