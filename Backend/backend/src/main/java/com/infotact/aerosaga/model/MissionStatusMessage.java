package com.infotact.aerosaga.model;

public class MissionStatusMessage {

    private String missionId;
    private String status;

    public MissionStatusMessage() {
    }

    public MissionStatusMessage(String missionId, String status) {
        this.missionId = missionId;
        this.status = status;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}