package com.infotact.aerosaga.temporal.workflow;

import com.infotact.aerosaga.temporal.MissionStatus;
import com.infotact.aerosaga.temporal.activity.DroneMissionActivities;
import com.infotact.aerosaga.temporal.activity.MissionStatusActivity;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class DroneMissionWorkflowImpl implements DroneMissionWorkflow {

    private final DroneMissionActivities activities =
            Workflow.newActivityStub(
                    DroneMissionActivities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofMinutes(1))
                            .setRetryOptions(
                                    RetryOptions.newBuilder()
                                            .setMaximumAttempts(1)
                                            .build()
                            )
                            .build()
            );

    private final MissionStatusActivity statusActivity =
            Workflow.newActivityStub(
                    MissionStatusActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofMinutes(1))
                            .setRetryOptions(
                                    RetryOptions.newBuilder()
                                            .setMaximumAttempts(1)
                                            .build()
                            )
                            .build()
            );

    @Override
    public String executeMission(String missionId) {

        Saga.Options sagaOptions = new Saga.Options.Builder()
                .setContinueWithError(true)
                .build();

        Saga saga = new Saga(sagaOptions);

        MissionStatus status = MissionStatus.PENDING;

        statusActivity.publishMissionStatus(
                missionId,
                status.name()
        );

        try {

            // 1. Preparing
            status = MissionStatus.PREPARING;

            statusActivity.publishMissionStatus(
                    missionId,
                    status.name()
            );

            Workflow.getLogger(DroneMissionWorkflowImpl.class)
                    .info(
                            "Mission {} status: {}",
                            missionId,
                            status
                    );

            activities.prepareDrone(missionId);

            saga.addCompensation(
                    activities::compensatePreparation,
                    missionId
            );

            // 2. Launch
            activities.launchDrone(missionId);

            status = MissionStatus.LAUNCHED;

            statusActivity.publishMissionStatus(
                    missionId,
                    status.name()
            );

            Workflow.getLogger(DroneMissionWorkflowImpl.class)
                    .info(
                            "Mission {} status: {}",
                            missionId,
                            status
                    );

            saga.addCompensation(
                    activities::compensateLaunch,
                    missionId
            );

            // 3. Mission in progress
            status = MissionStatus.IN_PROGRESS;

            statusActivity.publishMissionStatus(
                    missionId,
                    status.name()
            );

            Workflow.getLogger(DroneMissionWorkflowImpl.class)
                    .info(
                            "Mission {} status: {}",
                            missionId,
                            status
                    );

            activities.reachStartPoint(missionId);

            activities.reachMidPath(missionId);

            activities.reachDestination(missionId);

            activities.completeMission(missionId);

            // 4. Mission completed
            status = MissionStatus.COMPLETED;

            statusActivity.publishMissionStatus(
                    missionId,
                    status.name()
            );

            Workflow.getLogger(DroneMissionWorkflowImpl.class)
                    .info(
                            "Mission {} status: {}",
                            missionId,
                            status
                    );

            return "Mission completed: " + missionId;

        } catch (Exception e) {

            // 5. Mission failed
            status = MissionStatus.FAILED;

            statusActivity.publishMissionStatus(
                    missionId,
                    status.name()
            );

            Workflow.getLogger(DroneMissionWorkflowImpl.class)
                    .error(
                            "Mission {} status: {}",
                            missionId,
                            status
                    );

            // 6. Compensation
            status = MissionStatus.COMPENSATING;

            statusActivity.publishMissionStatus(
                    missionId,
                    status.name()
            );

            Workflow.getLogger(DroneMissionWorkflowImpl.class)
                    .info(
                            "Mission {} status: {}",
                            missionId,
                            status
                    );

            saga.compensate();

            throw e;
        }
    }
}