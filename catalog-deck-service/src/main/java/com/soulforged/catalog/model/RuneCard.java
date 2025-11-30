package com.soulforged.catalog.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("RUNE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RuneCard extends CardTemplate {
private String buffCode; // e.g. +attack, +defense
private int durationTurns;

public RuneCard(String code, String name, String description, Rarity rarity, ElementType element, String imageUrl, java.util.Set<String> tags, String buffCode, int durationTurns) {
super(code, name, description, CardCategory.RUNE, rarity, element, imageUrl, tags);
this.buffCode = buffCode;
this.durationTurns = durationTurns;
}
}