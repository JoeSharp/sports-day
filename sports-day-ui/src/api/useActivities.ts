import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { ActivityDTO, NewActivityDTO } from './types';
import useAuthContext from './useAuthContext';

interface UseActivities {
  isLoading: boolean;
  error: Error | null;
  activities: ActivityDTO[];
  addActivity: (activity: NewActivityDTO) => void;
  deleteActivity: (id: string) => void;
}

const DEFAULT_ACTIVITIES: ActivityDTO[] = [];

function useActivities(): UseActivities {
  const { accessToken } = useAuthContext();
  const queryClient = useQueryClient();

  const {
    isLoading,
    error,
    data: activities = DEFAULT_ACTIVITIES,
  } = useQuery<ActivityDTO[], Error>({
    enabled: !!accessToken,
    queryKey: ['activities'],
    queryFn: () =>
      fetch(`${import.meta.env.VITE_API_HOST}/api/activities`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }).then((r) => r.json()),
  });

  const addMutation = useMutation<ActivityDTO, Error, NewActivityDTO, unknown>({
    mutationFn: (newActivity) => {
      return fetch(`${import.meta.env.VITE_API_HOST}/api/activities`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newActivity),
      }).then((r) => r.json());
    },
    onSuccess: (newActivity) => {
      queryClient.setQueryData(['activities'], (old: ActivityDTO[]) => [
        ...old,
        newActivity,
      ]);
    },
  });

  const deleteMutation = useMutation<any, Error, string, unknown>({
    mutationFn: (activityId: string) => {
      return fetch(
        `${import.meta.env.VITE_API_HOST}/api/activities/${activityId}`,
        {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
    },
    onSuccess: (_result, activityId, _context) => {
      queryClient.setQueryData(['activities'], (old: ActivityDTO[]) =>
        old.filter(({ id }) => id != activityId)
      );
    },
  });

  return {
    isLoading,
    error,
    activities,
    addActivity: addMutation.mutate,
    deleteActivity: deleteMutation.mutate,
  };
}

export default useActivities;
