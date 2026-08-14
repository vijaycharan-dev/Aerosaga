package com.infotact.aerosaga.controller;

import com.infotact.aerosaga.model.Telemetry;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TelemetryController {

    @MessageMapping("/telemetry")
    @SendTo("/topic/telemetry")
    public Telemetry sendTelemetry(Telemetry telemetry) {
        return telemetry;
    }
}