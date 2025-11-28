package com.soulforged.duel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soulforged.duel.model.GameSession;
import com.soulforged.duel.model.TurnLog;
import com.soulforged.duel.repository.GameSessionRepository;
import com.soulforged.duel.repository.TurnLogRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class DuelService {

    private final GameSessionRepository gameSessionRepository;
    private final TurnLogRepository turnLogRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DuelService(GameSessionRepository gameSessionRepository,
                       TurnLogRepository turnLogRepository) {
        this.gameSessionRepository = gameSessionRepository;
        this.turnLogRepository = turnLogRepository;
    }

    public GameSession startDuel(Long playerOneId, Long playerTwoId) {
        GameSession session = new GameSession();
        session.setPlayerOneId(playerOneId);
        session.setPlayerTwoId(playerTwoId);

        // JSON CORRIGIDO
        session.setStateJson("{\"turn\":1,\"status\":\"ONGOING\"}");

        return gameSessionRepository.save(session);
    }

    public Optional<GameSession> getSession(Long id) {
        return gameSessionRepository.findById(id);
    }

    public Optional<GameSession> advanceTurn(Long sessionId, String actionDescription) {
        return gameSessionRepository.findById(sessionId).map(session -> {
            try {
                JsonNode node = objectMapper.readTree(session.getStateJson());
                int currentTurn = node.get("turn").asInt();

                ((com.fasterxml.jackson.databind.node.ObjectNode) node)
                        .put("turn", currentTurn + 1);

                session.setStateJson(objectMapper.writeValueAsString(node));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            GameSession saved = gameSessionRepository.save(session);

            TurnLog log = new TurnLog();
            log.setGameSessionId(saved.getId());
            log.setTurnNumber(getTurnFromState(saved.getStateJson()));
            log.setActionDescription(actionDescription);
            turnLogRepository.save(log);

            return saved;
        });
    }

    private int getTurnFromState(String stateJson) {
        try {
            JsonNode node = objectMapper.readTree(stateJson);
            return node.get("turn").asInt();
        } catch (IOException e) {
            return 0;
        }
    }
}
