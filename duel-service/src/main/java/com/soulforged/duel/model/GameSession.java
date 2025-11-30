package com.soulforged.duel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game_sessions")
@Getter
@Setter
@NoArgsConstructor
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerOneId;
    private Long playerTwoId;

    @Column(length = 2000)
    private String stateJson;

    private boolean finished = false;
}
