package com.infotact.aerosaga.temporal.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface DroneMissionActivities {

    @ActivityMethod
    void prepareDrone(String missionId);

    @ActivityMethod
    void launchDrone(String missionId);

    @ActivityMethod
    void reachStartPoint(String missionId);

    @ActivityMethod
    void reachMidPath(String missionId);

    @ActivityMethod
    void reachDestination(String missionId);

    @ActivityMethod
    void completeMission(String missionId);

    @ActivityMethod
    void compensateLaunch(String missionId);

    @ActivityMethod
    void compensatePreparation(String missionId);
}