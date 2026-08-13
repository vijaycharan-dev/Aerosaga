function DroneCard({ droneId, status, battery, altitude }) {
  return (
    <div>
      <h3>{droneId}</h3>
      <p>Status: {status}</p>
      <p>Battery: {battery}%</p>
      <p>Altitude: {altitude} m</p>
    </div>
  );
}

export default DroneCard;