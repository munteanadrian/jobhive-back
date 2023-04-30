package tech.adrianmuntean.jobhive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.adrianmuntean.jobhive.DTO.LoginDTO;
import tech.adrianmuntean.jobhive.DTO.SignupDTO;
import tech.adrianmuntean.jobhive.model.ROLE;
import tech.adrianmuntean.jobhive.model.User;
import tech.adrianmuntean.jobhive.repository.UserRepository;
import tech.adrianmuntean.jobhive.response.JwtResponse;
import tech.adrianmuntean.jobhive.response.MessageResponse;
import tech.adrianmuntean.jobhive.security.jwt.JwtUtils;
import tech.adrianmuntean.jobhive.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600) // change origin to my website?
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final
    AuthenticationManager authenticationManager;

    private final
    UserRepository userRepository;

    private final
    PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal(); // User?
        return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User();
        user.setDTO(signupRequest);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(ROLE.USER);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User " + user.getEmail() + " registered successfully!"));
    }
}
