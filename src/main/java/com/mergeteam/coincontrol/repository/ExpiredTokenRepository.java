package com.mergeteam.coincontrol.repository;

import com.mergeteam.coincontrol.entity.ExpiredToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpiredTokenRepository extends JpaRepository<ExpiredToken, UUID> {
}
