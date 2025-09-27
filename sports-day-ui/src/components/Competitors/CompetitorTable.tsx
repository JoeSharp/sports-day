import React from 'react';
import useCompetitors from '../../api/useCompetitors';

function CompetitorTable() {
  const { competitors, deleteCompetitor } = useCompetitors();

  const onDelete: React.MouseEventHandler<HTMLButtonElement> =
    React.useCallback(
      ({
        target,
      }) => {
        if (!(target instanceof HTMLButtonElement))
          return;

        if (!target.dataset.competitorId) return;

        deleteCompetitor(target.dataset.competitorId);
      },
      []
    );

  return (
    <>
      <h2>Competitors</h2>
      <table id="competitors">
        <thead>
          <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {competitors.map((competitor) => (
            <tr key={competitor.id} data-competitor-id={competitor.id}>
              <td>{competitor.name}</td>
              <td>{competitor.type}</td>
              <td>
                <button id={`delete-${competitor.id}`} data-competitor-id={competitor.id} onClick={onDelete}>
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

export default CompetitorTable;
