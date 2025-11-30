package com.soulforged.duel.model;

import java.util.UUID;

public class CardInstance {

    private String instanceId;
    private CardTemplate template;
    private int currentHealth;

    private boolean exhausted;

    public CardInstance(CardTemplate template) {
        this.instanceId = UUID.randomUUID().toString();
        this.template = template;
        this.currentHealth = template.getHealth();
        this.exhausted = false;
    }

    public String getInstanceId() { return instanceId; }
    public CardTemplate getTemplate() { return template; }

    public int getCurrentHealth() { return currentHealth; }
    public void setCurrentHealth(int currentHealth) { this.currentHealth = currentHealth; }

    public boolean isExhausted() { return exhausted; }
    public void setExhausted(boolean exhausted) { this.exhausted = exhausted; }
}

