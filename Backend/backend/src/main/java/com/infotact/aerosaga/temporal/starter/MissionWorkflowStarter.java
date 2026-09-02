package com.infotact.aerosaga.temporal.starter;

import com.infotact.aerosaga.temporal.worker.TemporalWorker;
import com.infotact.aerosaga.temporal.workflow.DroneMissionWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

public class MissionWorkflowStarter {

    public static void main(String[] args) {

        WorkflowServiceStubs service =
                WorkflowServiceStubs.newServiceStubs(
                        WorkflowServiceStubsOptions.newBuilder()
                                .setTarget("localhost:7233")
                                .build()
                );

        WorkflowClient client =
                WorkflowClient.newInstance(service);

        String missionId = "MISSION-003";

        DroneMissionWorkflow workflow =
                client.newWorkflowStub(
                        DroneMissionWorkflow.class,
                        WorkflowOptions.newBuilder()
                                .setWorkflowId("aerosaga-" + missionId)
                                .setTaskQueue(TemporalWorker.TASK_QUEUE)
                                .build()
                );

        System.out.println("Starting mission: " + missionId);

        String result = workflow.executeMission(missionId);

        System.out.println("Workflow result: " + result);
    }
}