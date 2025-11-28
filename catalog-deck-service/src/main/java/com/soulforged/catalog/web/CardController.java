package com.soulforged.catalog.web;

import com.soulforged.catalog.web.dto.CardTemplateDto;
import com.soulforged.catalog.service.CardTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/catalog/cards")
public class CardController {
private final CardTemplateService service;

public CardController(CardTemplateService service) {
this.service = service;
}

@GetMapping
public List<CardTemplateDto> all() {
return service.findAll();
}

@GetMapping("/{code}")
public CardTemplateDto byCode(@PathVariable String code) {
return service.findByCode(code);
}

@PostMapping
public ResponseEntity<CardTemplateDto> create(@RequestBody CardTemplateDto dto) {
CardTemplateDto created = service.create(dto);
return ResponseEntity.created(URI.create("/catalog/cards/" + created.getCode())).body(created);
}

@PutMapping("/{code}")
public CardTemplateDto update(@PathVariable String code, @RequestBody CardTemplateDto dto) {
return service.update(code, dto);
}

@DeleteMapping("/{code}")
public void delete(@PathVariable String code) {
service.delete(code);
}
}