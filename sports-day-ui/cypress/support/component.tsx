// ***********************************************************
// This example support/component.ts is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import './commands'
import '../../src/reset.css';
import '../../src/index.css';

import { mount } from 'cypress/react'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { AuthContext } from '../../src/api/useAuthContext';
import useAuth from '../../src/api/useAuth';

// Augment the Cypress namespace to include type definitions for
// your custom command.
// Alternatively, can be defined in cypress/support/component.d.ts
// with a <reference path="./component" /> at the top of your spec.
declare global {
  namespace Cypress {
    interface Chainable {
      mount: typeof mount
    }
  }
}

const queryClient = new QueryClient();

const authContextValue: UseAuth = {
  accessToken: 'test',
  name: 'Test User',
  login: () => { },
  refresh: () => { },
  logout: () => { }
}

Cypress.Commands.add('mount',
  (component, options = {}) => {
    const wrapped = (
      <QueryClientProvider client= { queryClient } >
      <AuthContext.Provider value={ authContextValue }>
        { component }
        </AuthContext.Provider>
        </QueryClientProvider>
    );
return mount(wrapped, options);
  });

// Example use:
// cy.mount(<MyComponent />)
