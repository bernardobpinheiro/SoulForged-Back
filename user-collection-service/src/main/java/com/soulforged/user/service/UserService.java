package com.soulforged.user.service;

import com.soulforged.user.model.Collection;
import com.soulforged.user.model.User;
import com.soulforged.user.repository.CollectionRepository;
import com.soulforged.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CollectionRepository collectionRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       CollectionRepository collectionRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.collectionRepository = collectionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String email, String rawPassword) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));

        User saved = userRepository.save(user);

        Collection collection = new Collection();
        collection.setUser(saved);
        collectionRepository.save(collection);

        return saved;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }
}
