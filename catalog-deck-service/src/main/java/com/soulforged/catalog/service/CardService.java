package com.soulforged.catalog.service;

import com.soulforged.catalog.model.CardTemplate;
import com.soulforged.catalog.repository.CardTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final CardTemplateRepository cardTemplateRepository;

    public CardService(CardTemplateRepository cardTemplateRepository) {
        this.cardTemplateRepository = cardTemplateRepository;
    }

    public List<CardTemplate> listAll() {
        return cardTemplateRepository.findAll();
    }

    public CardTemplate create(CardTemplate cardTemplate) {
        return cardTemplateRepository.save(cardTemplate);
    }
}
