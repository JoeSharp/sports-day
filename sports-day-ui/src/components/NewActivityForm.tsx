import React from 'react';
import useActivities from '../api/useActivities';

function NewActivityForm() {
  const { addActivity } = useActivities();
  const [name, setName] = React.useState<string>('');
  const [description, setDescription] = React.useState<string>('');

  const onNameChange: React.ChangeEventHandler<HTMLInputElement> =
    React.useCallback(({ target: { value } }) => setName(value), []);
  const onDescriptionChange: React.ChangeEventHandler<HTMLInputElement> =
    React.useCallback(({ target: { value } }) => setDescription(value), []);
  const onSubmit: React.FormEventHandler = React.useCallback(
    (e) => {
      e.preventDefault();
      addActivity({ name, description });
    },
    [name, description, addActivity]
  );

  return (
    <div>
      <h2>New Activity</h2>
      <form onSubmit={onSubmit}>
        <label htmlFor="newActivityName">Name</label>
        <input
          type="text"
          name="newActivityName"
          value={name}
          onChange={onNameChange}
        />

        <label htmlFor="newActivityDescription">Description</label>
        <input
          type="text"
          name="newActivityDescription"
          value={description}
          onChange={onDescriptionChange}
        />

        <input type="submit" value="Add Activity" />
      </form>
    </div>
  );
}

export default NewActivityForm;
