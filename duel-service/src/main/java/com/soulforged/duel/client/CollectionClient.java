package com.soulforged.duel.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class CollectionClient {

    private final RestTemplate rest = new RestTemplate();
    private final String URL = "http://localhost:8081/collection/";

    public List<String> getOwnedCards(Long playerId) {
        Map data = rest.getForObject(URL + playerId, Map.class);
        return (List<String>) data.get("cardsOwned");
    }
}
