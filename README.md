# sports-day

A Makefile is included which outlines most of the common development commands and their dependencies.
Consult this for typical tasks

## Networking
In order for containers to be able to reach eachother, and for auth tokens to have a valid source, I am using the following
* A custom IP address, registered in your machine, that all containers bind to and listen on
* Use nip.io domains so that we can create certificates with real domain resolutions, without having to modify /etc/hosts

The Makefile sets up LOCAL_STACK, but if you want to run scripts and commands outside of the Makefile, you will just need
to create the LOCAL_STACK env var in your shell.

## Running Locally
There are two ways to run locally

* Run dependencies in docker, run the application in bootrun, and the UI with Vite dev server
* Run everything in docker

The first will be useful when you are making changes to the service or UI.
The second one will be useful for when you are verifying the containerised version of the application.

Check the Makefile for the right targets
