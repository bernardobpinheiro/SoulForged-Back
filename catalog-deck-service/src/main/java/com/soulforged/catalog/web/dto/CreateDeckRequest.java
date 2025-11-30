package com.soulforged.catalog.web.dto;

import java.util.List;

public class CreateDeckRequest {

    private Long ownerUserId;
    private String name;
    private List<DeckCardRequest> cards;

    public Long getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(Long ownerUserId) { this.ownerUserId = ownerUserId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<DeckCardRequest> getCards() { return cards; }
    public void setCards(List<DeckCardRequest> cards) { this.cards = cards; }

    public static class DeckCardRequest {
        private String cardTemplateCode;
        private int quantity;

        public String getCardTemplateCode() { return cardTemplateCode; }
        public void setCardTemplateCode(String cardTemplateCode) { this.cardTemplateCode = cardTemplateCode; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
