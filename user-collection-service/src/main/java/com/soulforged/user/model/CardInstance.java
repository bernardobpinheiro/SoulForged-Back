package com.soulforged.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card_instances")
@Getter
@Setter
@NoArgsConstructor
public class CardInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cardTemplateCode;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;
}
