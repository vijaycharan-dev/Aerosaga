package com.infotact.aerosaga.temporal.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DroneMissionActivitiesImpl implements DroneMissionActivities {

    private static final Logger log =
            LoggerFactory.getLogger(DroneMissionActivitiesImpl.class);

    @Override
    public void prepareDrone(String missionId) {
        log.info("Preparing drone for mission: {}", missionId);
    }

    @Override
    public void launchDrone(String missionId) {
        log.info("Launching drone for mission: {}", missionId);

        //throw new RuntimeException("Simulated failure during drone launch for mission: " + missionId);
    }

    @Override
    public void reachStartPoint(String missionId) {
        log.info("Drone reached start point for mission: {}", missionId);
    }

    @Override
    public void reachMidPath(String missionId) {
        log.info("Drone reached mid path for mission: {}", missionId);
    }

    @Override
    public void reachDestination(String missionId) {
        log.info("Drone reached destination for mission: {}", missionId);
    }

    @Override
    public void completeMission(String missionId) {
        log.info("Completing mission for mission: {}", missionId);
    }

    @Override
    public void compensateLaunch(String missionId) {
        log.info("Compensating drone launch for mission: {}", missionId);
    }

    @Override
    public void compensatePreparation(String missionId) {
        log.info("Compensating drone preparation for mission: {}", missionId);
    }
}