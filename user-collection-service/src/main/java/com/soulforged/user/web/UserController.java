package com.soulforged.user.web;

import com.soulforged.user.model.Collection;
import com.soulforged.user.model.User;
import com.soulforged.user.repository.UserRepository;
import com.soulforged.user.service.CollectionService;
import com.soulforged.user.web.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final CollectionService collectionService;

    public UserController(UserRepository userRepository,
                          CollectionService collectionService) {
        this.userRepository = userRepository;
        this.collectionService = collectionService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getEmail())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/collection")
    public ResponseEntity<Collection> getCollection(@PathVariable Long id) {
        return collectionService.getCollectionForUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
