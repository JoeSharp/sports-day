import React from 'react';

export interface UseAuth {
  accessToken: string | undefined;
  name: string | undefined;
  login: (username: string, password: string) => void;
  refresh: () => void;
  logout: () => void;
}

function useAuth(): UseAuth {
  const [name, setName] = React.useState<string>();
  const [accessToken, setAccessToken] = React.useState<string>();
  const [refreshToken, setRefreshToken] = React.useState<string>();

  const onAccessTokenChange = React.useCallback((access_token: string) => {
    setAccessToken(access_token);
    const parts = access_token.split('.');
    const decoded = JSON.parse(atob(parts[1]));
    setName(decoded.name);
  }, []);

  const login = React.useCallback(
    (username: string, password: string) =>
      fetch(`${import.meta.env.VITE_API_HOST}/api/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
          username,
          password,
        }),
      })
        .then((r) => r.json())
        .then(({ accessToken, refreshToken }) => {
          setRefreshToken(refreshToken);
          onAccessTokenChange(accessToken);
        }),
    []
  );

  const refresh = React.useCallback(
    () =>
      refreshToken &&
      fetch(`${import.meta.env.VITE_API_HOST}/api/auth/refresh`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          Authorization: `Bearer ${accessToken}`,
        },
        body: new URLSearchParams({
          refreshToken,
        }),
      })
        .then((r) => r.json())
        .then(({ accessToken, refreshToken }) => {
          setRefreshToken(refreshToken);
          onAccessTokenChange(accessToken);
        }),
    [accessToken, refreshToken]
  );

  const logout = React.useCallback(() => {
    setAccessToken(undefined);
    setRefreshToken(undefined);
    refreshToken &&
      fetch(`${import.meta.env.VITE_API_HOST}/api/auth/logout`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          Authorization: `Bearer ${accessToken}`,
        },
        body: new URLSearchParams({
          refreshToken,
        }),
      });
  }, [accessToken, refreshToken]);

  return {
    name,
    accessToken,
    login,
    refresh,
    logout,
  };
}

export default useAuth;
