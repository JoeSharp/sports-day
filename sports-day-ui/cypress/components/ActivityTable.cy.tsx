import React from 'react'
import ActivityTable from '../../src/components/ActivityTable'

describe('<ActivityTable />', () => {
  beforeEach(() => {
    cy.defineAllIntercepts();
  });

  it('shows activities', () => {
    cy.mount(
      <ActivityTable />
    );

    cy.get('table#activities').should('exist');
  });

  it('allows deletion of activities', () => {
    cy.mount(
      <ActivityTable />
    );

    cy.get('tr[data-activity-id=123]').should('exist');
    cy.get('button[data-activity-id=123]')
      .click();
    cy.wait("@deleteActivity");
    cy.get('tr[data-activity-id=123]').should('not.exist');
    cy.get('tr[data-activity-id=456]').should('exist');
  });
});
