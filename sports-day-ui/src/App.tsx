import useActivities from './api/useActivities';

function App() {
  const { activities } = useActivities();

  return <div>
    <h1>Sports Day</h1>

    <table>
      <thead>
        <tr><th>Name</th><th>Description</th></tr>
      </thead>
      <tbody>
        {activities.map(activity => <tr key={activity.id}><td>{activity.name}</td><td>{activity.description}</td></tr>)}
      </tbody>
    </table>
  </div>
}

export default App
