package com.soulforged.user.web;

import com.soulforged.user.model.User;
import com.soulforged.user.security.JwtService;
import com.soulforged.user.service.UserService;
import com.soulforged.user.web.dto.LoginRequest;
import com.soulforged.user.web.dto.LoginResponse;
import com.soulforged.user.web.dto.RegisterRequest;
import com.soulforged.user.web.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request.getUsername(), request.getEmail(), request.getPassword());
        UserDTO dto = new UserDTO(user.getId(), user.getUsername(), user.getEmail());
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return userService.findByUsername(request.getUsername())
                .filter(user -> userService.checkPassword(user, request.getPassword()))
                .map(user -> {
                    String token = jwtService.generateToken(user.getUsername());
                    return ResponseEntity.ok(new LoginResponse(token));
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}
