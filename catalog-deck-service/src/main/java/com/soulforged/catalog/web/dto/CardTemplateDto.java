package com.soulforged.catalog.web.dto;

import com.soulforged.catalog.model.CardCategory;
import com.soulforged.catalog.model.ElementType;
import com.soulforged.catalog.model.Rarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardTemplateDto {
private String code;
private String name;
private String description;
private CardCategory category;
private Rarity rarity;
private ElementType element;
private String imageUrl;
private Set<String> tags;

// hero fields
private Integer hp;
private Integer attack;
private Integer defense;
private Integer manaCost;
private String abilityCode;

// spell fields
private Integer power;
private Boolean areaOfEffect;

// artifact fields
private String passiveEffectCode;
private Integer durability;

// rune fields
private String buffCode;
private Integer durationTurns;
}