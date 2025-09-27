import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { TeamDTO, NewTeamDTO } from './types';
import useAuthContext from './useAuthContext';

interface Useteams {
  isLoading: boolean;
  error: Error | null;
  teams: TeamDTO[];
  addTeam: (team: NewTeamDTO) => void;
  deleteTeam: (id: string) => void;
}

const DEFAULT_TEAMS: TeamDTO[] = [];

function useteams(): Useteams {
  const { accessToken } = useAuthContext();
  const queryClient = useQueryClient();

  const {
    isLoading,
    error,
    data: teams = DEFAULT_TEAMS,
  } = useQuery<TeamDTO[], Error>({
    enabled: !!accessToken,
    queryKey: ['teams'],
    queryFn: () =>
      fetch(`${import.meta.env.VITE_API_HOST}/api/teams`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        mode: 'cors',
      }).then((r) => r.json()),
  });

  const addMutation = useMutation<TeamDTO, Error, NewTeamDTO, unknown>({
    mutationFn: (newTeam) => {
      return fetch(`${import.meta.env.VITE_API_HOST}/api/teams`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
        mode: 'cors',
        body: JSON.stringify(newTeam),
      }).then((r) => r.json());
    },
    onSuccess: (newTeam) => {
      queryClient.setQueryData(['teams'], (old: TeamDTO[]) => [
        ...old,
        newTeam,
      ]);
    },
  });

  const deleteMutation = useMutation<any, Error, string, unknown>({
    mutationFn: (teamId: string) => {
      return fetch(
        `${import.meta.env.VITE_API_HOST}/api/teams/${teamId}`,
        {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
          mode: 'cors',
        }
      );
    },
    onSuccess: (_result, teamId, _context) => {
      queryClient.setQueryData(['teams'], (old: TeamDTO[]) =>
        old.filter(({ id }) => id != teamId)
      );
    },
  });

  return {
    isLoading,
    error,
    teams,
    addTeam: addMutation.mutate,
    deleteTeam: deleteMutation.mutate,
  };
}

export default useteams;
