package com.soulforged.duel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "turn_logs")
@Getter
@Setter
@NoArgsConstructor
public class TurnLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameSessionId;

    private int turnNumber;

    @Column(length = 1000)
    private String actionDescription;
}
