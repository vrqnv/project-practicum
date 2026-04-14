package ru.tbank.practicum.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BlindsService {
    private static final Logger log = LoggerFactory.getLogger(BlindsService.class);
    private String state = "closed";

    public void setState(String state) {
        this.state = state;
        log.info("Жалюзи: {}", state.equals("open") ? "открыты" : "закрыты");
    }

    public String getState() {
        return state;
    }
}