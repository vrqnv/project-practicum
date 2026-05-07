package ru.tbank.practicum.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.tbank.practicum.service.BatteryService;
import ru.tbank.practicum.service.BlindsService;
import ru.tbank.practicum.service.CurtainService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BatteryService batteryService;

    @MockitoBean
    private BlindsService blindsService;

    @MockitoBean
    private CurtainService curtainService;

    @Test
    void setBatteryTemperature_shouldReturnOk() throws Exception {
        doNothing().when(batteryService).setTemperature(anyLong(), anyInt());

        mockMvc.perform(put("/api/devices/battery/temperature")
                        .param("roomId", "1")
                        .param("value", "25"))
                .andExpect(status().isOk())
                .andExpect(content().string("Температура батареи в комнате 1 установлена на 25°C"));
    }

    @Test
    void setBlindsState_shouldReturnOk() throws Exception {
        doNothing().when(blindsService).setState(anyLong(), anyString());

        mockMvc.perform(put("/api/devices/blinds")
                        .param("roomId", "1")
                        .param("state", "open"))
                .andExpect(status().isOk())
                .andExpect(content().string("Жалюзи в комнате 1 открыты"));
    }

    @Test
    void setBlindsState_closed_shouldReturnOk() throws Exception {
        doNothing().when(blindsService).setState(anyLong(), anyString());

        mockMvc.perform(put("/api/devices/blinds")
                        .param("roomId", "1")
                        .param("state", "closed"))
                .andExpect(status().isOk())
                .andExpect(content().string("Жалюзи в комнате 1 закрыты"));
    }

    @Test
    void setCurtainSchedule_shouldReturnOk() throws Exception {
        doNothing().when(curtainService).setSchedule(anyLong(), anyString(), anyString());

        mockMvc.perform(post("/api/devices/curtains/schedule")
                        .param("roomId", "1")
                        .param("time", "08:00")
                        .param("action", "open"))
                .andExpect(status().isOk())
                .andExpect(content().string("Расписание для комнаты 1 установлено: в 08:00 open шторы"));
    }

    @Test
    void createBattery_shouldReturnOk() throws Exception {
        doNothing().when(batteryService).createBattery(anyLong());

        mockMvc.perform(post("/api/devices/battery")
                        .param("roomId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Батарея создана для комнаты 1 с температурой 22°C"));
    }

    @Test
    void createBlinds_shouldReturnOk() throws Exception {
        doNothing().when(blindsService).createBlinds(anyLong());

        mockMvc.perform(post("/api/devices/blinds")
                        .param("roomId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Жалюзи созданы для комнаты 1 в положении closed"));
    }
}