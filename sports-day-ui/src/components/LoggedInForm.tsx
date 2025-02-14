import useAuthContext from '../api/useAuthContext';

function LoggedInForm() {
  const { accessToken, name, refresh, logout } = useAuthContext();

  if (!accessToken) return undefined;

  return (
    <div>
      <p>Logged in as {name}</p>
      <button onClick={logout}>Logout</button>
      <button onClick={refresh}>Refresh</button>
    </div>
  );
}

export default LoggedInForm;
