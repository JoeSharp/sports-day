import React from 'react';
import useTeams from '../../api/useTeams';

function teamTable() {
  const { teams, deleteTeam } = useTeams();

  const onDelete: React.MouseEventHandler<HTMLButtonElement> =
    React.useCallback(
      ({
        target,
      }) => {
        if (!(target instanceof HTMLButtonElement))
          return;

        if (!target.dataset.teamId) return;

        deleteTeam(target.dataset.teamId);
      },
      []
    );

  return (
    <>
      <h2>Teams</h2>
      <table id="teams">
        <thead>
          <tr>
            <th>Name</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {teams.map((team) => (
            <tr key={team.id} data-team-id={team.id}>
              <td>{team.name}</td>
              <td>
                <button id={`delete-${team.id}`} data-team-id={team.id} onClick={onDelete}>
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

export default teamTable;
