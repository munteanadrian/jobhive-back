package tech.adrianmuntean.jobhive.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.adrianmuntean.jobhive.response.MessageResponse;
import tech.adrianmuntean.jobhive.service.UserService;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Others
    @GetMapping("/greeting")
    @PreAuthorize("isAuthenticated()")
    public MessageResponse userAccess() {
        return new MessageResponse("Congratulations! You are an authenticated user.");
    }
}
