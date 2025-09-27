import React from 'react';
import useActivities from '../../api/useActivities';

function ActivityTable() {
  const { activities, deleteActivity } = useActivities();

  const onDelete: React.MouseEventHandler<HTMLButtonElement> =
    React.useCallback(
      ({
        target,
      }) => {
        if (!(target instanceof HTMLButtonElement))
          return;

        if (!target.dataset.activityId) return;

        deleteActivity(target.dataset.activityId);
      },
      []
    );

  return (
    <>
      <h2>Activities</h2>
      <table id="activities">
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {activities.map((activity) => (
            <tr key={activity.id} data-activity-id={activity.id}>
              <td>{activity.name}</td>
              <td>{activity.description}</td>
              <td>
                <button id={`delete-${activity.id}`} data-activity-id={activity.id} onClick={onDelete}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}

export default ActivityTable;
