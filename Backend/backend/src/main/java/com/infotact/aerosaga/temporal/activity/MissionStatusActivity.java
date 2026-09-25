package com.infotact.aerosaga.temporal.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MissionStatusActivity {

    @ActivityMethod
    void publishMissionStatus(String missionId, String status);
}