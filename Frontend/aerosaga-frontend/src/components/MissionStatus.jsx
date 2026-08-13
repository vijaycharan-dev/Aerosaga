function MissionStatus({ missionName, status }) {
  return (
    <section>
      <h2>Mission Status</h2>
      <p>Mission: {missionName}</p>
      <p>Status: {status}</p>
    </section>
  );
}

export default MissionStatus;