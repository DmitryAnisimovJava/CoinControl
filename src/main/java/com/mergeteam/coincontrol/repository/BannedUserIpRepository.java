package com.mergeteam.coincontrol.repository;


import com.mergeteam.coincontrol.entity.BannedUserIp;
import io.hypersistence.utils.hibernate.type.basic.Inet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BannedUserIpRepository extends JpaRepository<BannedUserIp, UUID> {

    Optional<BannedUserIp> findByBannedIp(Inet bannedIp);
}
