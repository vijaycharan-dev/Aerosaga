package com.infotact.aerosaga.controller;

import com.infotact.aerosaga.model.MissionStatusMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mission-status")
public class MissionStatusController {

    private final SimpMessagingTemplate messagingTemplate;

    public MissionStatusController(
            SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public MissionStatusMessage publishStatus(
            @RequestBody MissionStatusMessage message) {

        messagingTemplate.convertAndSend(
                "/topic/mission-status",
                message
        );

        return message;
    }
}