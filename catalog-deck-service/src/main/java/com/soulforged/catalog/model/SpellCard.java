package com.soulforged.catalog.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SPELL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SpellCard extends CardTemplate {
private int manaCost;
private int power; // magnitude of effect (damage/heal)
private boolean areaOfEffect; // affects multiple targets

public SpellCard(String code, String name, String description, Rarity rarity, ElementType element, String imageUrl, java.util.Set<String> tags, int manaCost, int power, boolean areaOfEffect) {
super(code, name, description, CardCategory.SPELL, rarity, element, imageUrl, tags);
this.manaCost = manaCost;
this.power = power;
this.areaOfEffect = areaOfEffect;
}
}