package com.soulforged.duel.model;

import java.util.List;

public class DuelState {

    private Long playerOneId;
    private Long playerTwoId;

    private PlayerState playerOne;
    private PlayerState playerTwo;

    private int turn;
    private Long currentPlayer;
    private String status;
    private Long winner;

    public static DuelState initial(Long p1, Long p2, List<CardTemplate> deck1, List<CardTemplate> deck2) {
        DuelState state = new DuelState();

        state.playerOneId = p1;
        state.playerTwoId = p2;

        state.playerOne = PlayerState.initial(p1, deck1);
        state.playerTwo = PlayerState.initial(p2, deck2);

        state.turn = 1;
        state.currentPlayer = p1;
        state.status = "ONGOING";
        state.winner = null;

        return state;
    }

    public PlayerState getPlayer(Long id) {
        return playerOneId.equals(id) ? playerOne : playerTwo;
    }

    public PlayerState getEnemy(Long id) {
        return playerOneId.equals(id) ? playerTwo : playerOne;
    }

    public Long getPlayerOneId() { return playerOneId; }
    public Long getPlayerTwoId() { return playerTwoId; }
    public PlayerState getPlayerOne() { return playerOne; }
    public PlayerState getPlayerTwo() { return playerTwo; }

    public int getTurn() { return turn; }
    public void setTurn(int turn) { this.turn = turn; }

    public Long getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(Long currentPlayer) { this.currentPlayer = currentPlayer; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getWinner() { return winner; }
    public void setWinner(Long winner) { this.winner = winner; }
}

