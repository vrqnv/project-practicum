package ru.tbank.practicum.repository;

import ru.tbank.practicum.entity.BatteryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BatteryRepository extends JpaRepository<BatteryEntity, Long> {
    Optional<BatteryEntity> findByRoomId(Long roomId);
}