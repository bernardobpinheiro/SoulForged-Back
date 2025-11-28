package com.soulforged.duel.web;

import com.soulforged.duel.model.GameSession;
import com.soulforged.duel.service.DuelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/duels")
public class DuelController {

    private final DuelService duelService;

    public DuelController(DuelService duelService) {
        this.duelService = duelService;
    }

    @PostMapping("/start")
    public ResponseEntity<GameSession> start(@RequestParam Long playerOneId,
                                             @RequestParam Long playerTwoId) {
        GameSession session = duelService.startDuel(playerOneId, playerTwoId);
        return ResponseEntity.created(URI.create("/duels/" + session.getId())).body(session);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameSession> get(@PathVariable Long id) {
        Optional<GameSession> session = duelService.getSession(id);
        return session.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/turn")
    public ResponseEntity<GameSession> playTurn(@PathVariable Long id,
                                                @RequestParam String action) {
        return duelService.advanceTurn(id, action)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
