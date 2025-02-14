import React from 'react';
import useAuthContext from '../api/useAuthContext';

function LoginForm() {
  const { login } = useAuthContext();
  const [username, setUsername] = React.useState<string>('');
  const [password, setPassword] = React.useState<string>('');
  const onSubmit = React.useCallback(
    (e) => {
      e.preventDefault();

      login(username, password);
    },
    [username, password, login]
  );

  const onUsernameChange: React.ChangeEventHandler<HTMLInputElement> =
    React.useCallback(({ target: { value } }) => setUsername(value), []);
  const onPasswordChange: React.ChangeEventHandler<HTMLInputElement> =
    React.useCallback(({ target: { value } }) => setPassword(value), []);

  return (
    <form onSubmit={onSubmit}>
      <label htmlFor="username">Username</label>
      <input
        type="text"
        name="username"
        value={username}
        onChange={onUsernameChange}
      />

      <label htmlFor="password">Password</label>
      <input
        type="password"
        name="password"
        value={password}
        onChange={onPasswordChange}
      />
      <input type="submit" value="Login" />
    </form>
  );
}

export default LoginForm;
