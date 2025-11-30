package com.soulforged.duel.controller;

import com.soulforged.duel.dto.ActionRequest;
import com.soulforged.duel.dto.StartDuelRequest;
import com.soulforged.duel.model.GameSession;
import com.soulforged.duel.service.DuelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/duel")
public class DuelController {

    private final DuelService service;

    public DuelController(DuelService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public ResponseEntity<?> start(@RequestBody StartDuelRequest req) {
        try {
            return ResponseEntity.ok(service.start(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return service.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/action")
    public ResponseEntity<?> action(
            @PathVariable Long id,
            @RequestBody ActionRequest req
    ) {
        try {
            GameSession updated = service.processAction(id, req);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
