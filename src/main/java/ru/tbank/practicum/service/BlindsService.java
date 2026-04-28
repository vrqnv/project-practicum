package ru.tbank.practicum.service;

import ru.tbank.practicum.entity.BlindsEntity;
import ru.tbank.practicum.repository.BlindsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlindsService {

    private static final Logger log = LoggerFactory.getLogger(BlindsService.class);
    private final BlindsRepository blindsRepository;

    public BlindsService(BlindsRepository blindsRepository) {
        this.blindsRepository = blindsRepository;
    }

    @Transactional
    public void setState(Long roomId, String state) {
        BlindsEntity blinds = blindsRepository.findByRoomId(roomId)
                .orElse(new BlindsEntity());
        blinds.setRoomId(roomId);
        blinds.setState(state);
        blindsRepository.save(blinds);
        log.info("Жалюзи в комнате {}: {}", roomId, state.equals("open") ? "открыты" : "закрыты");
    }

    @Transactional(readOnly = true)
    public String getState(Long roomId) {
        return blindsRepository.findByRoomId(roomId)
                .map(BlindsEntity::getState)
                .orElse("closed");
    }
}