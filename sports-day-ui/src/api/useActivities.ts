import React from 'react';
import { ActivityDTO } from './types';

interface UseActivities {
  activities: ActivityDTO[];
}

const DEFAULT_ACTIVITIES: ActivityDTO[] = [
  {
    id: '123',
    name: 'Running',
    description: 'Pelting round the estate',
  },
  {
    id: '456',
    name: 'Swimming',
    description: 'Pretending to be a shark',
  },
];

function useActivities(): UseActivities {
  const [activities, setActivities] =
    React.useState<ActivityDTO[]>(DEFAULT_ACTIVITIES);

  React.useEffect(() => {
    fetch('http://localhost:8080/activities')
      .then((r) => r.json())
      .then(setActivities);
  }, []);

  return {
    activities,
  };
}

export default useActivities;
