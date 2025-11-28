package com.soulforged.user.repository;

import com.soulforged.user.model.Collection;
import com.soulforged.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Optional<Collection> findByUser(User user);
}
