package com.soulforged.catalog.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("HERO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HeroCard extends CardTemplate {

private int hp;
private int attack;
private int defense;
private int manaCost;

private String abilityCode;

public HeroCard(String code, String name, String description, Rarity rarity, ElementType element, String imageUrl, java.util.Set<String> tags, int hp, int attack, int defense, int manaCost, String abilityCode) {
super(code, name, description, CardCategory.HERO, rarity, element, imageUrl, tags);
this.hp = hp;
this.attack = attack;
this.defense = defense;
this.manaCost = manaCost;
this.abilityCode = abilityCode;
}
}