package com.infotact.aerosaga.temporal.worker;

import com.infotact.aerosaga.temporal.activity.DroneMissionActivitiesImpl;
import com.infotact.aerosaga.temporal.workflow.DroneMissionWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class TemporalWorker {

    public static final String TASK_QUEUE = "AEROSAGA_MISSION_QUEUE";

    public static void main(String[] args) {

        WorkflowServiceStubs service =
                WorkflowServiceStubs.newServiceStubs(
                        WorkflowServiceStubsOptions.newBuilder()
                                .setTarget("localhost:7233")
                                .build()
                );

        WorkflowClient client = WorkflowClient.newInstance(service);

        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(
                DroneMissionWorkflowImpl.class
        );

        worker.registerActivitiesImplementations(
                new DroneMissionActivitiesImpl()
        );

        factory.start();

        System.out.println(
                "AeroSaga Temporal Worker started. Task Queue: "
                        + TASK_QUEUE
        );
    }
}