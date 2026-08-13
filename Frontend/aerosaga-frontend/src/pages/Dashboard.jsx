import Header from "../components/Header";
import DroneCard from "../components/DroneCard";
import MissionStatus from "../components/MissionStatus";
import TelemetryPanel from "../components/TelemetryPanel";

function Dashboard() {
  return (
    <div>
      <Header />

      <main>
        <h2>Drone Dashboard</h2>

        <MissionStatus
          missionName="Demo Mission"
          status="READY"
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