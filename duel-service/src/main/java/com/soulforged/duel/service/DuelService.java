package com.soulforged.duel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.soulforged.duel.client.CatalogClient;
import com.soulforged.duel.client.CollectionClient;
import com.soulforged.duel.dto.ActionRequest;
import com.soulforged.duel.dto.StartDuelRequest;
import com.soulforged.duel.model.*;
import com.soulforged.duel.repository.GameSessionRepository;
import com.soulforged.duel.repository.TurnLogRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DuelService {

    private final GameSessionRepository gameSessionRepo;
    private final TurnLogRepository turnLogRepo;
    private final CatalogClient catalogClient;
    private final CollectionClient collectionClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public DuelService(GameSessionRepository gameSessionRepo,
                       TurnLogRepository turnLogRepo,
                       CatalogClient catalogClient,
                       CollectionClient collectionClient) {

        this.gameSessionRepo = gameSessionRepo;
        this.turnLogRepo = turnLogRepo;
        this.catalogClient = catalogClient;
        this.collectionClient = collectionClient;
    }

    // -------------------------
    // 1. INICIAR DUELO
    // -------------------------
    public GameSession start(StartDuelRequest req) {

        // Validar se o jogador realmente possui essas cartas
        validatePlayerDeck(req.getPlayerOneId(), req.getDeckOne());
        validatePlayerDeck(req.getPlayerTwoId(), req.getDeckTwo());

        // Criar estado inicial em JSON
        DuelState initialState = DuelState.initial(
                req.getPlayerOneId(),
                req.getPlayerTwoId(),
                loadDeck(req.getDeckOne()),
                loadDeck(req.getDeckTwo())
        );

        GameSession session = new GameSession();
        session.setPlayerOneId(req.getPlayerOneId());
        session.setPlayerTwoId(req.getPlayerTwoId());
        session.setStateJson(toJson(initialState));

        GameSession saved = gameSessionRepo.save(session);

        // Primeiro log
        TurnLog log = new TurnLog();
        log.setGameSessionId(saved.getId());
        log.setTurnNumber(1);
        log.setActionDescription("Duelo iniciado");
        turnLogRepo.save(log);

        return saved;
    }

    // -------------------------
    // 2. PEGAR ESTADO ATUAL
    // -------------------------
    public Optional<GameSession> get(Long id) {
        return gameSessionRepo.findById(id);
    }

    // -------------------------
    // 3. PROCESSAR AÇÕES: jogar carta / atacar / passar turno
    // -------------------------
    public GameSession processAction(Long sessionId, ActionRequest action) {

        GameSession session = gameSessionRepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

        DuelState state = fromJson(session.getStateJson(), DuelState.class);

        switch (action.getType()) {
    case PLAY_CARD:
        playCard(state, action);
        break;

    case ATTACK:
        attack(state, action);
        break;

    case PASS:
        passTurn(state, action);
        break;

    default:
        throw new RuntimeException("Ação inválida: " + action.getType());
}


        // Salvar estado
        session.setStateJson(toJson(state));
        GameSession saved = gameSessionRepo.save(session);

        // Registrar log
        TurnLog log = new TurnLog();
        log.setGameSessionId(session.getId());
        log.setTurnNumber(state.getTurn());
        log.setActionDescription(action.getDescription());
        turnLogRepo.save(log);

        return saved;
    }

    // -------------------------
    // 3.1 JOGAR CARTA
    // -------------------------
    private void playCard(DuelState state, ActionRequest req) {
        PlayerState player = state.getPlayer(req.getPlayerId());

        if (player.getCurrentMana() < req.getManaCost())
            throw new RuntimeException("Mana insuficiente");

        CardTemplate template = catalogClient.getCard(req.getCardCode());
        CardInstance instance = new CardInstance(template);

        player.setCurrentMana(player.getCurrentMana() - req.getManaCost());
        player.getBoard().add(instance);
        player.getHand().removeIf(c -> c.getTemplate().getCode().equals(req.getCardCode()));
    }

    // -------------------------
    // 3.2 ATAQUE
    // -------------------------
    private void attack(DuelState state, ActionRequest req) {
        PlayerState me = state.getPlayer(req.getPlayerId());
        PlayerState enemy = state.getEnemy(req.getPlayerId());

        CardInstance attacker =
                me.getBoard().get(req.getAttackerIndex());

        attacker.setExhausted(true);

        if (req.getTargetIndex() == -1) {
            // atacar jogador
            enemy.setLife(enemy.getLife() - attacker.getTemplate().getAttack());
            if (enemy.getLife() <= 0) {
                state.setWinner(req.getPlayerId());
                state.setStatus("FINISHED");
            }
            return;
        }

        CardInstance target =
                enemy.getBoard().get(req.getTargetIndex());

        target.setCurrentHealth(target.getCurrentHealth() - attacker.getTemplate().getAttack());
        attacker.setCurrentHealth(attacker.getCurrentHealth() - target.getTemplate().getAttack());

        if (target.getCurrentHealth() <= 0) enemy.getBoard().remove(target);
        if (attacker.getCurrentHealth() <= 0) me.getBoard().remove(attacker);
    }

    // -------------------------
    // 3.3 PASSAR TURNO
    // -------------------------
    private void passTurn(DuelState state, ActionRequest req) {
        if (state.getCurrentPlayer() != req.getPlayerId())
            throw new RuntimeException("Não é seu turno");

        state.setTurn(state.getTurn() + 1);
        state.setCurrentPlayer(
                state.getCurrentPlayer() == state.getPlayerOneId() ?
                        state.getPlayerTwoId() :
                        state.getPlayerOneId()
        );

        PlayerState next = state.getPlayer(state.getCurrentPlayer());

        // regenerar mana
        int newMax = Math.min(10, next.getMaxMana() + 1);
        next.setMaxMana(newMax);
        next.setCurrentMana(newMax);

        // comprar carta
        if (!next.getDeck().isEmpty()) {
            next.getHand().add(
                    new CardInstance(next.getDeck().remove(0))
            );
        }

        next.getBoard().forEach(c -> c.setExhausted(false));
    }

    // -------------------------
    // FERRAMENTAS INTERNAS
    // -------------------------
    private void validatePlayerDeck(Long playerId, List<String> codes) {
        List<String> owned = collectionClient.getOwnedCards(playerId);
        for (String c : codes) {
            if (!owned.contains(c))
                throw new RuntimeException("Jogador " + playerId + " não possui carta " + c);
        }
    }

    private List<CardTemplate> loadDeck(List<String> codes) {
        List<CardTemplate> deck = new ArrayList<>();
        for (String code : codes) {
            deck.add(catalogClient.getCard(code));
        }
        Collections.shuffle(deck);
        return deck;
    }

    private String toJson(Object o) {
        try { return mapper.writeValueAsString(o); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    private <T> T fromJson(String json, Class<T> type) {
        try { return mapper.readValue(json, type); }
        catch (Exception e) { throw new RuntimeException(e); }
    }
}
