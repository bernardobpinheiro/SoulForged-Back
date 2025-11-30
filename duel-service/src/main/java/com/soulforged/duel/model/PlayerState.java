package com.soulforged.duel.model;

import java.util.*;

public class PlayerState {

    private Long userId;

    private List<CardTemplate> deck;
    private List<CardInstance> hand;
    private List<CardInstance> board;
    private List<CardInstance> graveyard;

    private int life;
    private int maxMana;
    private int currentMana;

    public static PlayerState initial(Long userId, List<CardTemplate> deck) {
        PlayerState ps = new PlayerState();
        ps.userId = userId;

        ps.deck = new ArrayList<>(deck);
        Collections.shuffle(ps.deck);

        ps.hand = new ArrayList<>();
        ps.board = new ArrayList<>();
        ps.graveyard = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            if (!ps.deck.isEmpty()) {
                ps.hand.add(new CardInstance(ps.deck.remove(0)));
            }
        }

        ps.life = 30;
        ps.maxMana = 1;
        ps.currentMana = 1;

        return ps;
    }

    public Long getUserId() { return userId; }

    public List<CardTemplate> getDeck() { return deck; }
    public List<CardInstance> getHand() { return hand; }
    public List<CardInstance> getBoard() { return board; }
    public List<CardInstance> getGraveyard() { return graveyard; }

    public int getLife() { return life; }
    public void setLife(int life) { this.life = life; }

    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }

    public int getCurrentMana() { return currentMana; }
    public void setCurrentMana(int currentMana) { this.currentMana = currentMana; }
}

