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

## Service
The nodeport on a service is one that can be reached using the minikube IP, bypassing the ingress, but of course it's a random port


