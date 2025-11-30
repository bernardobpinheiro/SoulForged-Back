package com.soulforged.common.dto;

import com.soulforged.common.enums.Rarity;

public class DeckSummaryDTO {

    private Long id;
    private String name;
    private int cardCount;
    private Rarity highestRarity;

    public DeckSummaryDTO() {}

    public DeckSummaryDTO(Long id, String name, int cardCount, Rarity highestRarity) {
        this.id = id;
        this.name = name;
        this.cardCount = cardCount;
        this.highestRarity = highestRarity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCardCount() { return cardCount; }
    public void setCardCount(int cardCount) { this.cardCount = cardCount; }

    public Rarity getHighestRarity() { return highestRarity; }
    public void setHighestRarity(Rarity highestRarity) { this.highestRarity = highestRarity; }
}
