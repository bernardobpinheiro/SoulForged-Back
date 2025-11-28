package com.soulforged.catalog.repository;

import com.soulforged.catalog.model.DeckCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckCardRepository extends JpaRepository<DeckCard, Long> {
}
