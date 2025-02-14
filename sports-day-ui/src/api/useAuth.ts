import React from 'react';

interface UseAuth {
  accessToken: string;
  name: string;
  login: (username: string, password: string) => void;
  refresh: () => void;
  logout: () => void;
}

const CLIENT_ID = 'timesheets-service';
const CLIENT_SECRET = 'rX0uyWb89PxdeclkQoLMtmRtCLRxFlKy';

function useAuth(): UseAuth {
  const [name, setName] = React.useState<string>();
  const [accessToken, setAccessToken] = React.useState<string>();
  const [refreshToken, setRefreshToken] = React.useState<string>();

  const onAccessTokenChange = React.useCallback((access_token) => {
    setAccessToken(access_token);
    const parts = access_token.split('.');
    const decoded = JSON.parse(atob(parts[1]));
    setName(decoded.name);
  }, []);

  const getToken = React.useCallback(
    (body) => {
      fetch('/auth/realms/ratracejoe/protocol/openid-connect/token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
          client_id: CLIENT_ID,
          client_secret: CLIENT_SECRET,
          ...body,
        }),
      })
        .then((r) => r.json())
        .then(({ access_token, refresh_token }) => {
          setRefreshToken(refresh_token);
          onAccessTokenChange(access_token);
        });
    },
    [onAccessTokenChange]
  );

  const login = React.useCallback(
    (username: string, password: string) =>
      getToken({
        username,
        password,
        grant_type: 'password',
      }),
    [getToken]
  );

  const refresh = React.useCallback(
    () =>
      getToken({
        refresh_token: refreshToken,
        grant_type: 'refresh_token',
      }),
    [refreshToken, getToken]
  );

  const logout = React.useCallback(() => {
    setAccessToken(undefined);
    setRefreshToken(undefined);
    fetch('/auth/realms/ratracejoe/protocol/openid-connect/logout', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: new URLSearchParams({
        client_id: CLIENT_ID,
        client_secret: CLIENT_SECRET,
        refresh_token: refreshToken
      }),
    })
  }, [refreshToken]);

  return {
    name,
    accessToken,
    login,
    refresh,
    logout,
  };
}

export default useAuth;
