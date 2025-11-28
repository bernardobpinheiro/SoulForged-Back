package com.soulforged.catalog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "card_templates")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// REMOVIDO: @AllArgsConstructor (para evitar conflitos de construtor na herança)
@Data
@NoArgsConstructor
public abstract class CardTemplate {
    
    @Id
    protected String code; 

    protected String name;
    
    @Column(length = 1024)
    protected String description;

    // CORREÇÃO: Mantido o insertable/updatable = false para evitar duplicação
    // com a coluna Category que está sendo usada para o Discriminator.
    @Enumerated(EnumType.STRING)
    @Column(name = "category", insertable = false, updatable = false)
    protected CardCategory category;

    @Enumerated(EnumType.STRING)
    protected Rarity rarity;

    @Enumerated(EnumType.STRING)
    protected ElementType element;

    protected String imageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    protected Set<String> tags;

    // Construtor PROTEGIDO necessário para que as subclasses chamem super()
    protected CardTemplate(String code, String name, String description, CardCategory category, Rarity rarity, ElementType element, String imageUrl, Set<String> tags) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.category = category;
        this.rarity = rarity;
        this.element = element;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }
}