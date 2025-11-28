package com.soulforged.catalog.repository;

import com.soulforged.catalog.model.CardTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; 

@Repository
public interface CardTemplateRepository extends JpaRepository<CardTemplate, String> { 

    List<CardTemplate> findByCategory(String category);
    List<CardTemplate> findByRarity(String rarity);
    
    Optional<CardTemplate> findByCode(String code);

    boolean existsByCode(String code);
}