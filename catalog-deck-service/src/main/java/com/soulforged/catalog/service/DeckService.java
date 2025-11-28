package com.soulforged.catalog.service;

import com.soulforged.catalog.model.CardTemplate;
import com.soulforged.catalog.model.Deck;
import com.soulforged.catalog.model.DeckCard;
import com.soulforged.catalog.repository.CardTemplateRepository;
import com.soulforged.catalog.repository.DeckCardRepository;
import com.soulforged.catalog.repository.DeckRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService {

    private final DeckRepository deckRepository;
    private final DeckCardRepository deckCardRepository;
    private final CardTemplateRepository cardTemplateRepository;

    public DeckService(DeckRepository deckRepository,
                       DeckCardRepository deckCardRepository,
                       CardTemplateRepository cardTemplateRepository) {
        this.deckRepository = deckRepository;
        this.deckCardRepository = deckCardRepository;
        this.cardTemplateRepository = cardTemplateRepository;
    }

    public Deck createDeckWithCards(Deck deck, List<DeckCard> cards) {
        Deck savedDeck = deckRepository.save(deck);
        for (DeckCard dc : cards) {
            dc.setDeck(savedDeck);
            deckCardRepository.save(dc);
        }
        return savedDeck;
    }

    public Optional<Deck> getDeck(Long id) {
        return deckRepository.findById(id);
    }

    public List<Deck> listDecks() {
        return deckRepository.findAll();
    }

    public Optional<CardTemplate> findCardByCode(String code) {
        return cardTemplateRepository.findByCode(code);
    }
}
