import LoginForm from './components/LoginForm';
import ActivityTable from './components/Activities/ActivityTable';
import NewActivityForm from './components/Activities/NewActivityForm';
import CompetitorTable from './components/Competitors/CompetitorTable';
import TeamTable from './components/Teams/TeamTable';
import LoggedInForm from './components/LoggedInForm';
import useAuthContext from './api/useAuthContext';

function App() {
  const { accessToken } = useAuthContext();
  return (
    <div>
      <h1>Sports Day</h1>

      <LoggedInForm />
      {!accessToken && <LoginForm />}
      {!!accessToken && <ActivityTable />}
      {!!accessToken && <NewActivityForm />}
      {!!accessToken && <CompetitorTable />}
      {!!accessToken && <TeamTable />}
    </div>
  );
}

export default App;
