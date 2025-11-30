package com.soulforged.catalog.repository;

import com.soulforged.catalog.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {
}
