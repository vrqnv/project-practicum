package ru.tbank.practicum.service;

import static ru.tbank.practicum.util.AppConstants.DEFAULT_BLINDS_STATE;
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
    private static final String DEFAULT_STATE = DEFAULT_BLINDS_STATE;
    public BlindsService(BlindsRepository blindsRepository) {
        this.blindsRepository = blindsRepository;
    }

    @Transactional
    public void createBlinds(Long roomId) {
        if (blindsRepository.findByRoomId(roomId).isPresent()) {
            throw new RuntimeException("Жалюзи уже существуют в комнате " + roomId);
        }
        BlindsEntity blinds = new BlindsEntity();
        blinds.setRoomId(roomId);
        blinds.setState(DEFAULT_STATE);
        blindsRepository.save(blinds);
        log.info("Созданы жалюзи для комнаты {} в положении {}", roomId, DEFAULT_STATE);
    }

    @Transactional
    public void setState(Long roomId, String state) {
        BlindsEntity blinds = blindsRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Жалюзи не найдены в комнате " + roomId));
        blinds.setState(state);
        blindsRepository.save(blinds);
        log.info("Жалюзи в комнате {}: {}", roomId, state.equals("open") ? "открыты" : "закрыты");
    }

    @Transactional(readOnly = true)
    public String getState(Long roomId) {
        return blindsRepository.findByRoomId(roomId)
                .map(BlindsEntity::getState)
                .orElse(DEFAULT_STATE);
    }
}