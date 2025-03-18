describe("Manage Activities", () => {
  beforeEach(() => {
    cy.defineAllIntercepts();
  });

  it('allows creation of activities', () => {
    cy.loginToUi();
  });

});
