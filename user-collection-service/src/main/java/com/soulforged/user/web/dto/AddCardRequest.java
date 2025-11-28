package com.soulforged.user.web.dto;

public class AddCardRequest {

    private String cardTemplateCode;
    private int quantity;

    public String getCardTemplateCode() { return cardTemplateCode; }
    public void setCardTemplateCode(String cardTemplateCode) { this.cardTemplateCode = cardTemplateCode; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
