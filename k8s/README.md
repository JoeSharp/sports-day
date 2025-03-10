# K8s Learning

Since Kubernetes is quite a significant topic, I will capture useful commands as I'm learning my way through it.

## Running the example

kubectl apply -f k8s/example.yaml

This creates two deployments, a service for each, and then an ingress which can route appropriately.
The ingress is available on the minikube IP

```bash
# This command tells you the IP that minikube is available on locally
minikube ip
```

```bash
# Note that curl can be given a resolution for the lifecycle of the command
curl --resolve "hello-world.example:80:$(minikube ip)" -i http://hello-world.example/v2
```

by adding a resolution to /etc/hosts, we can access the URL via the browser (and dispense with the need for the --resolve flag)
```
$(minikube ip) hello-world.example
```

## Ingress concepts
Each ingress registered is given a hostname, this appears to be used within the Ingress Controller to figure out which ingress to call.
Each ingress is backed by a service
Note that we can give ports specific names in the YAML and then refer to them with those names.

### Keycloak
For Keycloak to behave correctly, the ingress needs to forward the right headers.
The file `k8s/ingress-forward.yaml` file contains a config map which will instruct the ingress to behave as required.
There are then flags on Keycloak itself (see the command)

## Service
The nodeport on a service is one that can be reached using the minikube IP, bypassing the ingress, but of course it's a random port

LoadBalancer service can be reached from outside, useful in Cloud, for minikube you have to use `minikube tunnel` to expose it.

ClusterIP is default, and makes the service reachable inside the cluster
<service-name>.<namepace>.svc.cluster.local
or simply
<service-name> if in same namespace

NodePort exposes service on static port on nodes IP, creates a random port and you can use `minikube ip` and that port to reach it.
