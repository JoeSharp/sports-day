import LoginForm from './components/LoginForm';
import ActivityTable from './components/ActivityTable';
import LoggedInForm from './components/LoggedInForm';
import NewActivityForm from './components/NewActivityForm';
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
    </div>
  );
}

export default App;
