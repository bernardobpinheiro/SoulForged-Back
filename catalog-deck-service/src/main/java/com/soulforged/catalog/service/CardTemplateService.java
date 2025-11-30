package com.soulforged.catalog.service;

import com.soulforged.catalog.web.dto.CardTemplateDto;
import com.soulforged.catalog.mapper.CardTemplateMapper;
import com.soulforged.catalog.model.CardTemplate;
import com.soulforged.catalog.repository.CardTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.soulforged.common.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardTemplateService {
private final CardTemplateRepository repo;

public CardTemplateService(CardTemplateRepository repo) {
this.repo = repo;
}

public List<CardTemplateDto> findAll() {
return repo.findAll().stream().map(CardTemplateMapper::toDto).collect(Collectors.toList());
}

public CardTemplateDto findByCode(String code) {
return repo.findById(code).map(CardTemplateMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Card not found: " + code));
}

public CardTemplateDto create(CardTemplateDto dto) {
if (dto.getCode() == null || dto.getCode().isBlank()) throw new IllegalArgumentException("code required");
if (repo.existsById(dto.getCode())) throw new IllegalStateException("exists");
CardTemplate saved = repo.save(CardTemplateMapper.toEntity(dto));
return CardTemplateMapper.toDto(saved);
}

public CardTemplateDto update(String code, CardTemplateDto dto) {
CardTemplate existing = repo.findById(code).orElseThrow(() -> new ResourceNotFoundException("Card not found: " + code));
// we reconstruct entity for simplicity; alternative: update fields
CardTemplate toSave = CardTemplateMapper.toEntity(dto);
// ensure same code
toSave.setCode(code);
CardTemplate saved = repo.save(toSave);
return CardTemplateMapper.toDto(saved);
}

public void delete(String code) {
if (!repo.existsById(code)) throw new ResourceNotFoundException("Card not found: " + code);
repo.deleteById(code);
}
}