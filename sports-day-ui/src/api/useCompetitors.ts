import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { CompetitorDTO, NewCompetitorDTO } from './types';
import useAuthContext from './useAuthContext';

interface Usecompetitors {
  isLoading: boolean;
  error: Error | null;
  competitors: CompetitorDTO[];
  addCompetitor: (competitor: NewCompetitorDTO) => void;
  deleteCompetitor: (id: string) => void;
}

const DEFAULT_COMPETITORS: CompetitorDTO[] = [];

function usecompetitors(): Usecompetitors {
  const { accessToken } = useAuthContext();
  const queryClient = useQueryClient();

  const {
    isLoading,
    error,
    data: competitors = DEFAULT_COMPETITORS,
  } = useQuery<CompetitorDTO[], Error>({
    enabled: !!accessToken,
    queryKey: ['competitors'],
    queryFn: () =>
      fetch(`${import.meta.env.VITE_API_HOST}/api/competitors`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        mode: 'cors',
      }).then((r) => r.json()),
  });

  const addMutation = useMutation<CompetitorDTO, Error, NewCompetitorDTO, unknown>({
    mutationFn: (newCompetitor) => {
      return fetch(`${import.meta.env.VITE_API_HOST}/api/competitors`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
        mode: 'cors',
        body: JSON.stringify(newCompetitor),
      }).then((r) => r.json());
    },
    onSuccess: (newCompetitor) => {
      queryClient.setQueryData(['competitors'], (old: CompetitorDTO[]) => [
        ...old,
        newCompetitor,
      ]);
    },
  });

  const deleteMutation = useMutation<any, Error, string, unknown>({
    mutationFn: (competitorId: string) => {
      return fetch(
        `${import.meta.env.VITE_API_HOST}/api/competitors/${competitorId}`,
        {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
          mode: 'cors',
        }
      );
    },
    onSuccess: (_result, competitorId, _context) => {
      queryClient.setQueryData(['competitors'], (old: CompetitorDTO[]) =>
        old.filter(({ id }) => id != competitorId)
      );
    },
  });

  return {
    isLoading,
    error,
    competitors,
    addCompetitor: addMutation.mutate,
    deleteCompetitor: deleteMutation.mutate,
  };
}

export default usecompetitors;
