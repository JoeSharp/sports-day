import React from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { ActivityDTO } from './types';
import useAuthContext from './useAuthContext';

interface UseActivities {
  isLoading: boolean;
  error: Error | null;
  activities: ActivityDTO[];
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
      fetch(`/api/activities`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }).then((r) => r.json()),
  });

  const addMutation = useMutation({
    mutationFn: (newActivity) => {
      return fetch('/api/activities', {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${accessToken}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newActivity)
      });
    },
    onSuccess: (res) => {
      res.json().then(newActivity =>
        queryClient.setQueryData(['activities'], (old) => [...old, newActivity]));
    }
  });

  const deleteMutation = useMutation({
    mutationFn: (activityId: string) => {
      fetch(`/api/activities/${activityId}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
    },
    onSuccess: (_result, activityId, _context) => {
      queryClient.setQueryData(['activities'], (old) =>
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
