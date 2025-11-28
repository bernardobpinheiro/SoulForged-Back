package com.soulforged.catalog.mapper;

import com.soulforged.catalog.web.dto.CardTemplateDto;
import com.soulforged.catalog.model.*; // Importa todas as Entidades/Subclasses
import com.soulforged.common.exceptions.ResourceNotFoundException; // Importe qualquer exceção necessária (se aplicável, mas o Service já trata)

import java.util.HashSet;
import java.util.Set;

public class CardTemplateMapper {

    // Mapeia Entidade (Subclasse) -> DTO (Contrato API de Saída)
    public static CardTemplateDto toDto(CardTemplate e) {
        if (e == null) return null;
        
        CardTemplateDto d = new CardTemplateDto();
        
        // 1. Mapear Campos Comuns
        d.setCode(e.getCode());
        d.setName(e.getName());
        d.setDescription(e.getDescription());
        d.setCategory(e.getCategory());
        d.setRarity(e.getRarity());
        d.setElement(e.getElement());
        d.setImageUrl(e.getImageUrl());
        d.setTags(e.getTags());
        
        // 2. Mapear Campos Específicos (usando instanceof)
        if (e instanceof HeroCard) {
            HeroCard h = (HeroCard) e;
            d.setHp(h.getHp());
            d.setAttack(h.getAttack());
            d.setDefense(h.getDefense());
            d.setManaCost(h.getManaCost());
            d.setAbilityCode(h.getAbilityCode());
            
        } else if (e instanceof SpellCard) {
            SpellCard s = (SpellCard) e;
            d.setManaCost(s.getManaCost());
            d.setPower(s.getPower());
            d.setAreaOfEffect(s.isAreaOfEffect());
            
        } else if (e instanceof ArtifactCard) {
            ArtifactCard a = (ArtifactCard) e;
            d.setPassiveEffectCode(a.getPassiveEffectCode());
            d.setDurability(a.getDurability());
            
        } else if (e instanceof RuneCard) {
            RuneCard r = (RuneCard) e;
            d.setBuffCode(r.getBuffCode());
            d.setDurationTurns(r.getDurationTurns());
        }
        
        return d;
    }

    // Mapeia DTO (Contrato API de Entrada) -> Entidade (Subclasse específica)
    // Usa o campo 'category' do DTO para decidir qual subclasse instanciar
    public static CardTemplate toEntity(CardTemplateDto d) {
        if (d == null) return null;
        
        // Usa Set.of() para tags; se o Set for null, usa Set vazio para evitar NPE
        Set<String> tags = d.getTags() == null ? Set.of() : d.getTags();
        
        // Define valores padrão para campos Integer/Boolean para evitar NPE
        int zero = 0;
        boolean boolFalse = false;
        
        switch (d.getCategory()) {
            case HERO:
                // Usa o construtor completo de HeroCard
                return new HeroCard(
                    d.getCode(), d.getName(), d.getDescription(), 
                    d.getRarity(), d.getElement(), d.getImageUrl(), tags, 
                    d.getHp() != null ? d.getHp() : zero, 
                    d.getAttack() != null ? d.getAttack() : zero, 
                    d.getDefense() != null ? d.getDefense() : zero, 
                    d.getManaCost() != null ? d.getManaCost() : zero, 
                    d.getAbilityCode()
                );
            case SPELL:
                // Usa o construtor completo de SpellCard
                return new SpellCard(
                    d.getCode(), d.getName(), d.getDescription(), 
                    d.getRarity(), d.getElement(), d.getImageUrl(), tags, 
                    d.getManaCost() != null ? d.getManaCost() : zero, 
                    d.getPower() != null ? d.getPower() : zero, 
                    d.getAreaOfEffect() != null ? d.getAreaOfEffect() : boolFalse
                );
            case ARTIFACT:
                // Usa o construtor completo de ArtifactCard
                return new ArtifactCard(
                    d.getCode(), d.getName(), d.getDescription(), 
                    d.getRarity(), d.getElement(), d.getImageUrl(), tags, 
                    d.getPassiveEffectCode(), 
                    d.getDurability() != null ? d.getDurability() : zero
                );
            case RUNE:
                // Usa o construtor completo de RuneCard
                return new RuneCard(
                    d.getCode(), d.getName(), d.getDescription(), 
                    d.getRarity(), d.getElement(), d.getImageUrl(), tags, 
                    d.getBuffCode(), 
                    d.getDurationTurns() != null ? d.getDurationTurns() : zero
                );
            default:
                // Se a categoria for inválida, lança exceção (o serviço já trata)
                throw new IllegalArgumentException("Unsupported category: " + d.getCategory());
        }
    }
}