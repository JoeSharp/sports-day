# sports-day

A Makefile is included which outlines most of the common development commands and their dependencies.
Consult this for typical tasks

# SDK
I use the amazing SDK Man to manage my java versions.
In the root of the project run the following, to ensure you are on the right java version
```bash
sdk env install
```

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

### Monitoring
The Kafbat Kafka UI is included in the docker compose file and can be accessed via
http://localhost:18080

Other dependencies can be monitored via Docker/K8s exec, commands in Makefile

## TLS
The application exposes the UI and service layers via HTTPS
For this to work via a Browser, you will need to import the Certificate of the `sportsday` Certificate Authority into your browser/operating system.

The curl scripts are setup to supply those files, so those should work out of the box.

## Macs are Special
Because Apple don't trust you to run Docker without setting your own hair on fire, the custom IP address will not be reachable from your host.
So when you stand the whole thing up, you will have to use this domain

https://sports-day-ui.127.0.0.1.nip.io:9443/

Like a muggle

## Minikube

Use the minikube docker container repo
```bash
# Use minikube docker environment
eval $(minikube -p minikube docker-env)

# Reset the docker env
eval $(minikube -p minikube docker-env | grep export | cut -d '=' -f1 | sed "s/export/unset/g")
```

