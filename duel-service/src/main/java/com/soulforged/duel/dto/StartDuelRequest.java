package com.soulforged.duel.dto;

import java.util.List;

public class StartDuelRequest {

    private Long playerOneId;
    private Long playerTwoId;

    private List<String> deckOne;
    private List<String> deckTwo;

    public Long getPlayerOneId() { return playerOneId; }
    public void setPlayerOneId(Long playerOneId) { this.playerOneId = playerOneId; }

    public Long getPlayerTwoId() { return playerTwoId; }
    public void setPlayerTwoId(Long playerTwoId) { this.playerTwoId = playerTwoId; }

    public List<String> getDeckOne() { return deckOne; }
    public void setDeckOne(List<String> deckOne) { this.deckOne = deckOne; }

    public List<String> getDeckTwo() { return deckTwo; }
    public void setDeckTwo(List<String> deckTwo) { this.deckTwo = deckTwo; }
}
