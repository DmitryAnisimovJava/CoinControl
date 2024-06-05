package com.mergeteam.coincontrol.entity;

import io.hypersistence.utils.hibernate.type.basic.Inet;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLInetType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "banned_user_ip", schema = "coin_repository", catalog = "coin")
@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class BannedUserIp implements BaseEntity<UUID> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private UUID id;
    @Type(PostgreSQLInetType.class)
    @Column(name = "banned_ip")
    private Inet bannedIp;
}
