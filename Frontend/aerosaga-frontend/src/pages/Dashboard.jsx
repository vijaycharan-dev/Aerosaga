import { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";

import Header from "../components/Header";
import DroneCard from "../components/DroneCard";
import MissionStatus from "../components/MissionStatus";
import TelemetryPanel from "../components/TelemetryPanel";

function Dashboard() {
  const [missionStatus, setMissionStatus] = useState({
    missionId: "Demo Mission",
    status: "READY",
  });

  useEffect(() => {
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",

      onConnect: () => {
        console.log("Dashboard connected to WebSocket");

        client.subscribe("/topic/mission-status", (message) => {
          const data = JSON.parse(message.body);

          console.log("Dashboard received mission status:", data);

          setMissionStatus({
            missionId: data.missionId,
            status: data.status,
          });
        });
      },

      onWebSocketError: (error) => {
        console.error("Dashboard WebSocket error:", error);
      },

      onStompError: (frame) => {
        console.error("Dashboard STOMP error:", frame);
      },
    });

    client.activate();

    return () => {
      client.deactivate();
    };
  }, []);

  return (
    <div>
      <Header />

      <main>
        <h2>Drone Dashboard</h2>

        <MissionStatus
          missionName={missionStatus.missionId}
          status={missionStatus.status}
        />

        <DroneCard
          droneId="DRONE-01"
          status="IDLE"
          battery={100}
          altitude={0}
        />

        <DroneCard
          droneId="DRONE-02"
          status="FLYING"
          battery={78}
          altitude={120}
        />

        <TelemetryPanel />
      </main>
    </div>
  );
}

export default Dashboard;