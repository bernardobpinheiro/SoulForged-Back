package com.soulforged.duel.dto;

import com.soulforged.duel.model.ActionType;

public class ActionRequest {

    private ActionType type;
    private Long playerId;

    // PLAY_CARD
    private String cardCode;
    private int manaCost;

    // ATTACK
    private int attackerIndex;
    private int targetIndex; // -1 = atacar jogador

    private String description;

    public ActionType getType() { return type; }
    public void setType(ActionType type) { this.type = type; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public String getCardCode() { return cardCode; }
    public void setCardCode(String cardCode) { this.cardCode = cardCode; }

    public int getManaCost() { return manaCost; }
    public void setManaCost(int manaCost) { this.manaCost = manaCost; }

    public int getAttackerIndex() { return attackerIndex; }
    public void setAttackerIndex(int attackerIndex) { this.attackerIndex = attackerIndex; }

    public int getTargetIndex() { return targetIndex; }
    public void setTargetIndex(int targetIndex) { this.targetIndex = targetIndex; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
