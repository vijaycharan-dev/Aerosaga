import { useState } from "react";

function TelemetryPanel() {
  const [battery, setBattery] = useState(78);
  const [altitude, setAltitude] = useState(120);

  return (
    <section>
      <h2>Live Telemetry</h2>

      <p>Drone ID: DRONE-01</p>
      <p>Status: FLYING</p>
      <p>Battery: {battery}%</p>
      <p>Altitude: {altitude} m</p>
      <p>Speed: 12.4 m/s</p>
      <p>Latitude: 17.385044</p>
      <p>Longitude: 78.486671</p>
      <button onClick={() => setBattery((previous) => previous - 1)}>
        Decrease Battery
      </button>

      <button onClick={() => setAltitude((previous) => previous + 10)}>
        Increase Altitude
      </button>
    </section>
  );
}

export default TelemetryPanel;