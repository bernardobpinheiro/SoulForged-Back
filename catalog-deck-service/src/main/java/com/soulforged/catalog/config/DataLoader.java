package com.soulforged.catalog.config;

import com.soulforged.catalog.model.*;
import com.soulforged.catalog.repository.CardTemplateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class DataLoader {
@Bean
CommandLineRunner seed(CardTemplateRepository repo) {
return args -> {
if (repo.count() == 0) {
// O construtor da superclasse espera:
// String code, String name, String description, Rarity rarity, ElementType element, String imageUrl, Set<String> tags

// --- HERO samples (HeroCard tem 7 da Superclasse + 5 próprios = 12 argumentos) ---
repo.save(new HeroCard(
    "H_FIRE_01", "Pyros", "A fiery warrior.", // Base (code, name, description)
    Rarity.EPIC, ElementType.FIRE, "/images/pyros.png", Set.of("starter", "melee"), // Base (rarity, element, imageUrl, tags)
    30, 8, 4, 3, "ABILITY_FLAME_STRIKE" // Específicos (hp, attack, defense, manaCost, abilityCode)
));
repo.save(new HeroCard(
    "H_WATER_01", "Aqua", "A water guardian.", // Base
    Rarity.RARE, ElementType.WATER, "/images/aqua.png", Set.of("starter", "support"), // Base
    28, 6, 6, 3, "ABILITY_WAVE" // Específicos
));

// --- SPELL sample (SpellCard tem 7 da Superclasse + 3 próprios = 10 argumentos) ---
repo.save(new SpellCard(
    "S_HEAL_01", "Minor Heal", "Restore a small amount.", // Base
    Rarity.COMMON, ElementType.WATER, "/images/heal.png", Set.of("heal"), // Base
    2, 6, false // Específicos (manaCost, power, areaOfEffect)
));

// --- ARTIFACT sample (ArtifactCard tem 7 da Superclasse + 2 próprios = 9 argumentos) ---
repo.save(new ArtifactCard(
    "A_SHIELD_01", "Ancient Shield", "Grants shield.", // Base
    Rarity.RARE, ElementType.EARTH, "/images/shield.png", Set.of("defense"), // Base
    "EFFECT_SHIELD_3", 3 // Específicos (passiveEffectCode, durability)
));

// --- RUNE sample (RuneCard tem 7 da Superclasse + 2 próprios = 9 argumentos) ---
repo.save(new RuneCard(
    "R_ATK_01", "Rune of Might", "Increase attack.", // Base
    Rarity.COMMON, ElementType.SHADOW, "/images/rune_atk.png", Set.of("buff"), // Base
    "+2_ATTACK", 2 // Específicos (buffCode, durationTurns)
));
}
};
}
}