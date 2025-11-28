package com.soulforged.catalog.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
// REMOVIDO: import lombok.AllArgsConstructor;

@Entity
@DiscriminatorValue("ARTIFACT")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ArtifactCard extends CardTemplate {
    
    private String passiveEffectCode; // code to explain effect
    private int durability; // how many turns it lasts / uses

    // Construtor manual que chama o construtor protegido da classe pai
    public ArtifactCard(String code, String name, String description, Rarity rarity, ElementType element, String imageUrl, java.util.Set<String> tags, String passiveEffectCode, int durability) {
        // A chave é chamar o construtor super() corretamente
        super(code, name, description, CardCategory.ARTIFACT, rarity, element, imageUrl, tags); 
        this.passiveEffectCode = passiveEffectCode;
        this.durability = durability;
    }
}