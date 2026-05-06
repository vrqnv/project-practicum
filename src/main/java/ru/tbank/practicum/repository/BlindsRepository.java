package ru.tbank.practicum.repository;

import ru.tbank.practicum.entity.BlindsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BlindsRepository extends JpaRepository<BlindsEntity, Long> {
    Optional<BlindsEntity> findByRoomId(Long roomId);
}