package com.infotact.aerosaga.temporal.workflow;

import com.infotact.aerosaga.temporal.activity.DroneMissionActivities;
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

    @Override
    public String executeMission(String missionId) {

        Saga.Options sagaOptions = new Saga.Options.Builder()
                .setContinueWithError(true)
                .build();

        Saga saga = new Saga(sagaOptions);

        try {

            activities.prepareDrone(missionId);

            saga.addCompensation(
                    activities::compensatePreparation,
                    missionId
            );

            activities.launchDrone(missionId);

            saga.addCompensation(
                    activities::compensateLaunch,
                    missionId
            );

            activities.completeMission(missionId);

            return "Mission completed: " + missionId;

        } catch (Exception e) {

            saga.compensate();

            throw e;
        }
    }
}