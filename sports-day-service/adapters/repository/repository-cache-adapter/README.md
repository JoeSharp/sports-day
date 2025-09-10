# Cache Repositories

This module provides a general way of using two concrete repository implementations.
One of them being the slower, but more persistent mechanism, the other being a faster more transient means.

For example
* SQL Database as main store
* Redis as a cache

or
* File based main store
* In memory as cache

Check the unit tests for examples of using, the tests use two in memory repositories.
The memory repositories are instrumented to count calls.