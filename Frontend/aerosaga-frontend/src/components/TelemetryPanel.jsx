import { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";

function TelemetryPanel() {
  const [telemetry, setTelemetry] = useState({
    droneId: "DRONE-01",
    status: "WAITING",
    battery: 78,
    altitude: 120,
    speed: 12.4,
    latitude: 17.385044,
    longitude: 78.486671,
  });

  useEffect(() => {
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",

      onConnect: () => {
        console.log("Connected to WebSocket");

        client.subscribe("/topic/telemetry", (message) => {
          const data = JSON.parse(message.body);

          console.log("Received telemetry:", data);

          setTelemetry(data);
        });
      },

      onWebSocketError: (error) => {
        console.error("WebSocket error:", error);
      },

      onStompError: (frame) => {
        console.error("STOMP error:", frame);
      },
    });

    client.activate();

    return () => {
      client.deactivate();
    };
  }, []);

  return (
    <section>
      <h2>Live Telemetry</h2>

      <p>Drone ID: {telemetry.droneId}</p>
      <p>Status: {telemetry.status}</p>
      <p>Battery: {telemetry.battery}%</p>
      <p>Altitude: {telemetry.altitude} m</p>
      <p>Speed: {telemetry.speed} m/s</p>
      <p>Latitude: {telemetry.latitude}</p>
      <p>Longitude: {telemetry.longitude}</p>

      <button
        onClick={() =>
          setTelemetry((previous) => ({
            ...previous,
            battery: previous.battery - 1,
          }))
        }
      >
        Decrease Battery
      </button>

      <button
        onClick={() =>
          setTelemetry((previous) => ({
            ...previous,
            altitude: previous.altitude + 10,
          }))
        }
      >
        Increase Altitude
      </button>
    </section>
  );
}

export default TelemetryPanel;