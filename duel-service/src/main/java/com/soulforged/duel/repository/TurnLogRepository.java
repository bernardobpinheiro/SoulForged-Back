package com.soulforged.duel.repository;

import com.soulforged.duel.model.TurnLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnLogRepository extends JpaRepository<TurnLog, Long> {
}
