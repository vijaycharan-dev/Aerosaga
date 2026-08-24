AeroSaga

1. Project Overview

AeroSaga is a drone mission management system combining backend services, real-time telemetry, and reliable workflow orchestration. It uses Spring Boot, React, WebSocket, Temporal, the Saga pattern, PostgreSQL, Docker, Maven, Git, and GitHub.

2. Problem Statement

Drone missions contain multiple dependent operations. If a later operation fails after earlier operations succeed, the system can be left partially completed. AeroSaga addresses reliable execution, failure handling, compensation, telemetry, and workflow visibility.

3. Objectives

Manage drone missions through reliable workflows.

Execute mission operations in sequence.

Handle activity failures and retries.

Compensate previously completed business operations.

Provide real-time telemetry.

Monitor workflow execution.

Provide reproducible Docker infrastructure.

4. System Architecture

React Frontend
      |
      v
Spring Boot Backend
      |
      +--------------------+
      |                    |
      v                    v
WebSocket             Temporal Workflow
Telemetry                   |
                            v
                  AEROSAGA_MISSION_QUEUE
                            |
                            v
                    Temporal Worker
                            |
                            v
                  Mission Activities

5. Technologies Used

      Technology
      Purpose
      Java
      Backend programming
      Spring Boot
      Backend/application development
      React
      Frontend UI
      WebSocket
      Real-time telemetry
      Temporal
      Durable workflow orchestration
      Saga Pattern
      Business-level compensation
      PostgreSQL
      Temporal persistence
      Docker
      Infrastructure/containerization
      Maven
      Build and dependency management
      Git/GitHub
      Version control and collaboration
      Temporal UI
      Workflow monitoring

6. Why These Technologies?

      Java: Enterprise-ready backend platform with strong ecosystem support.
      Spring Boot: Simplifies backend and API development.
      React: Component-based interactive frontend.
      WebSocket: Pushes frequently changing telemetry without repeated polling.
      Temporal: Provides durable workflows, activity execution, retries, and history.
      Saga: Provides business-level compensation after partial failure.
      PostgreSQL: Supports Temporal persistence.
      Docker: Gives the team consistent local infrastructure.
      Git/GitHub: Supports branches, commits, Pull Requests, and collaboration.

7. Frontend

      The React frontend provides the user interface, mission information, telemetry display, and real-time updates received through the backend/WebSocket layer.

8. Backend

      The backend uses Java and Spring Boot for application services, APIs, WebSocket communication, and integration with Temporal.
      Temporal source location:
            Backend/backend/src/main/java/com/infotact/aerosaga/temporal/

9. WebSocket Telemetry

Telemetry Source
      |
      v
Spring Boot WebSocket
      |
      v
React Frontend
      |
      v
Telemetry Dashboard

WebSocket is useful for frequently changing telemetry because the backend can push updates to connected clients.

10. Temporal Workflow

The workflow contract is DroneMissionWorkflow and its implementation is DroneMissionWorkflowImpl.

The mission flow is:

Prepare Drone
      |
      v
Launch Drone
      |
      v
Complete Mission

Activities are invoked through Temporal Activity stubs.

Temporal Worker

The worker uses task queue:

AEROSAGA_MISSION_QUEUE

Worker class:

TemporalWorker.java

Start it with:

./mvnw exec:java -Dexec.mainClass=com.infotact.aerosaga.temporal.worker.TemporalWorker

11. Saga Pattern

Saga provides business-level compensation. After successful steps, compensating operations are registered. If a later operation fails, the workflow calls saga.compensate().

Example:

Prepare Drone
      |
      +--> compensatePreparation()

Launch Drone
      |
      +--> compensateLaunch()

Saga compensation is business-level rollback, not a traditional database transaction rollback.

12. Docker Infrastructure

Start the infrastructure from the project root:

docker compose -f docker/docker-compose.yml up -d

Check:

docker ps

Expected services:

aerosaga-postgresql
aerosaga-temporal
aerosaga-temporal-ui

Common ports:

Service

Port

PostgreSQL

5432

Temporal Server

7233

Temporal UI

8233

13. Project Structure

AeroSaga/
├── Backend/
│   └── backend/
│       ├── pom.xml
│       └── src/main/java/com/infotact/aerosaga/temporal/
│           ├── activity/
│           │   ├── DroneMissionActivities.java
│           │   └── DroneMissionActivitiesImpl.java
│           ├── starter/
│           │   └── MissionWorkflowStarter.java
│           ├── worker/
│           │   └── TemporalWorker.java
│           └── workflow/
│               ├── DroneMissionWorkflow.java
│               └── DroneMissionWorkflowImpl.java
├── Frontend/
├── docker/
└── README.md

14. How to Run the Project

Step 1: Start Docker

cd /d/InfotactSolutions/Aerosaga
docker compose -f docker/docker-compose.yml up -d
docker ps

Step 2: Run Backend Tests

cd /d/InfotactSolutions/Aerosaga/Backend/backend
./mvnw clean test

Expected:

BUILD SUCCESS
Tests run: 1
Failures: 0
Errors: 0

Step 3: Start Temporal Worker

Open another terminal:

cd /d/InfotactSolutions/Aerosaga/Backend/backend
./mvnw exec:java -Dexec.mainClass=com.infotact.aerosaga.temporal.worker.TemporalWorker

Expected:

AeroSaga Temporal Worker started. Task Queue: AEROSAGA_MISSION_QUEUE

Keep this terminal running.

Step 4: Start a Mission

Open another terminal:

cd /d/InfotactSolutions/Aerosaga/Backend/backend
./mvnw exec:java -Dexec.mainClass=com.infotact.aerosaga.temporal.starter.MissionWorkflowStarter

Expected:

Starting mission: MISSION-XXX
Workflow result: Mission completed: MISSION-XXX

The Worker terminal should show:

Preparing drone for mission: MISSION-XXX
Launching drone for mission: MISSION-XXX
Completing mission: MISSION-XXX

Step 5: Open Temporal UI

Open:

http://localhost:8233

15. Successful Mission Flow

Mission Request
      |
      v
DroneMissionWorkflow
      |
      v
prepareDrone()
      |
      v
launchDrone()
      |
      v
completeMission()
      |
      v
Mission Completed

A successful test produced:

Workflow result: Mission completed: MISSION-006

16. Failure & Compensation Flow

Prepare Drone       ✓
      |
      v
Launch Drone        ✗
      |
      v
Activity Failure / Retry
      |
      v
Saga Compensation
      |
      v
Workflow Failure

During testing, launchDrone() was configured to throw a simulated failure. The failure was observed in Worker logs. For deterministic testing, maximumAttempts(1) can be used so compensation is easier to observe.

17. Team Contributions

The project includes:

Temporal workflow and Saga orchestration

Spring Boot and WebSocket backend

React telemetry dashboard

Frontend dashboard and visualization

Docker/Temporal infrastructure

Git/GitHub integration

Work was developed through feature branches and integrated through Pull Requests.

18. Git Workflow

develop
   |
   +----> feature branch
              |
              v
           Commit
              |
              v
            Push
              |
              v
        Pull Request
              |
              v
           develop

Example Temporal branch:

feature/vijay-temporal-workflow

19. Future Enhancements

Connect activities to real drone/device services.

Add application-specific mission persistence.

Add authentication and authorization.

Add Temporal Signals for pause/cancel operations.

Add Temporal Queries for workflow status.

Expand telemetry and monitoring.

Add automated workflow tests.

Run multiple workers for higher throughput.

Add production deployment infrastructure.

20. Testing

Run:

./mvnw clean test

The Temporal workflow has been tested for successful execution and controlled activity failure/compensation.

21. Key Takeaways

React
  |
  v
Spring Boot
  |
  +------> WebSocket ------> Real-Time Telemetry
  |
  v
Temporal Workflow
  |
  v
Temporal Worker
  |
  v
Activities
  |
  v
Saga Compensation on Failure

AeroSaga demonstrates reliable orchestration of multi-step drone missions while providing real-time visibility and structured failure compensation.