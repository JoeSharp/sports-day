import React from 'react';
import useAuth from './useAuth';

const DEFAULT_USE_AUTH: UseAuth = {
  token: 'none',
  login: () => {
    throw new Error('not implemented');
  },
};

export const AuthContext = React.createContext<UseAuth>(DEFAULT_USE_AUTH);
export const useAuthContext = () => React.useContext(AuthContext);

export const AuthContextProvider: React.FC<{ children: React.JSX.Element }> = ({
  children,
}) => {
  const value = useAuth();

  return (
    <AuthContext.Provider value={value}> {children} </AuthContext.Provider>
  );
};

export default useAuthContext;
