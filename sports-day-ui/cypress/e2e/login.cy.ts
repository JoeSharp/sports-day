describe('template spec', () => {
  beforeEach(() => {
    cy.defineAllIntercepts();
  });

  it('allows login', () => {
    cy.loginToUi();
  })
})
