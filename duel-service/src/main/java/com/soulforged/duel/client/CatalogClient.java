package com.soulforged.duel.client;

import com.soulforged.duel.model.CardTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CatalogClient {

    private final RestTemplate rest = new RestTemplate();
    private final String URL = "http://localhost:8082/cards/";

    public CardTemplate getCard(String code) {
        return rest.getForObject(URL + code, CardTemplate.class);
    }
}
