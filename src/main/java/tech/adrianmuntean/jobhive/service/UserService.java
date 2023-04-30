package tech.adrianmuntean.jobhive.service;

import org.springframework.stereotype.Service;
import tech.adrianmuntean.jobhive.model.User;
import tech.adrianmuntean.jobhive.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(User user) {
        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    public Object findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
