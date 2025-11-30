package com.soulforged.catalog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deck_cards")
@Getter
@Setter
@NoArgsConstructor
public class DeckCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @ManyToOne
    @JoinColumn(name = "card_template_id")
    private CardTemplate cardTemplate;

    @Column(nullable = false)
    private int quantity;
}
