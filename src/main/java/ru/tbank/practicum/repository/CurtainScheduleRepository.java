package ru.tbank.practicum.repository;

import ru.tbank.practicum.entity.CurtainScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CurtainScheduleRepository extends JpaRepository<CurtainScheduleEntity, Long> {
    Optional<CurtainScheduleEntity> findByRoomId(Long roomId);
}