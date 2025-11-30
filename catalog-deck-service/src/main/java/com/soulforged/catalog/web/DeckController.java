package com.soulforged.catalog.web;

import com.soulforged.catalog.model.CardTemplate;
import com.soulforged.catalog.model.Deck;
import com.soulforged.catalog.model.DeckCard;
import com.soulforged.catalog.service.DeckService;
import com.soulforged.catalog.web.dto.CreateDeckRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public List<Deck> list() {
        return deckService.listDecks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deck> get(@PathVariable Long id) {
        return deckService.getDeck(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Deck> create(@RequestBody CreateDeckRequest request) {
        Deck deck = new Deck();
        deck.setOwnerUserId(request.getOwnerUserId());
        deck.setName(request.getName());

        List<DeckCard> cards = new ArrayList<>();
        if (request.getCards() != null) {
            for (CreateDeckRequest.DeckCardRequest dcReq : request.getCards()) {
                DeckCard dc = new DeckCard();
                CardTemplate template = deckService.findCardByCode(dcReq.getCardTemplateCode())
                        .orElseThrow(() -> new IllegalArgumentException("Card code not found: " + dcReq.getCardTemplateCode()));
                dc.setCardTemplate(template);
                dc.setQuantity(dcReq.getQuantity());
                cards.add(dc);
            }
        }

        Deck saved = deckService.createDeckWithCards(deck, cards);
        return ResponseEntity.created(URI.create("/decks/" + saved.getId())).body(saved);
    }
}
