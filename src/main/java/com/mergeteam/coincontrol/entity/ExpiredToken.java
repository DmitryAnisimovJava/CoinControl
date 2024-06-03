package com.mergeteam.coincontrol.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "expired_tokens", schema = "coin_repository", catalog = "coin")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpiredToken {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "keep_until")
    private LocalDateTime keepUntil;
}
