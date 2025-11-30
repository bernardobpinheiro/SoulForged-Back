package com.soulforged.user.service;

import com.soulforged.user.model.CardInstance;
import com.soulforged.user.model.Collection;
import com.soulforged.user.model.User;
import com.soulforged.user.repository.CollectionRepository;
import com.soulforged.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;

    public CollectionService(CollectionRepository collectionRepository,
                             UserRepository userRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
    }

    public Optional<Collection> getCollectionForUser(Long userId) {
        return userRepository.findById(userId)
                .flatMap(collectionRepository::findByUser);
    }

    public Optional<Collection> addCardToUserCollection(Long userId, String cardTemplateCode, int quantity) {
        return userRepository.findById(userId)
                .flatMap(collectionRepository::findByUser)
                .map(collection -> {
                    CardInstance card = new CardInstance();
                    card.setCardTemplateCode(cardTemplateCode);
                    card.setQuantity(quantity);
                    card.setCollection(collection);
                    collection.getCards().add(card);
                    return collectionRepository.save(collection);
                });
    }
}
