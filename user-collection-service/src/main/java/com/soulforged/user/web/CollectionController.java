package com.soulforged.user.web;

import com.soulforged.user.model.Collection;
import com.soulforged.user.service.CollectionService;
import com.soulforged.user.web.dto.AddCardRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping("/{userId}/cards")
    public ResponseEntity<Collection> addCard(@PathVariable Long userId,
                                              @RequestBody AddCardRequest request) {
        return collectionService.addCardToUserCollection(userId, request.getCardTemplateCode(), request.getQuantity())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
