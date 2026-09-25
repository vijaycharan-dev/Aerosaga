package com.infotact.aerosaga.temporal.activity;

import org.springframework.web.client.RestTemplate;

public class MissionStatusActivityImpl
        implements MissionStatusActivity {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String STATUS_URL = "http://localhost:8080/api/mission-status";

    @Override
    public void publishMissionStatus(
            String missionId,
            String status) {

        MissionStatusRequest request = new MissionStatusRequest(
                missionId,
                status);

        restTemplate.postForObject(
                STATUS_URL,
                request,
                String.class);

        System.out.println(
                "Published mission status: "
                        + missionId
                        + " -> "
                        + status);
    }

    private record MissionStatusRequest(
            String missionId,
            String status) {
    }
}