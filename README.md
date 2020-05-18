# branching-strategy-workshop-demo

![Build and test master](https://github.com/NabilCC/branching-strategy-workshop-demo/workflows/Build%20and%20test%20master/badge.svg?branch=master)

## Building With Docker

To build a Docker image:

`./gradlew clean build && docker image build -t workshop-demo:latest .`

To start a new Docker container and access the application:

`docker run --name workshop-demo -d -p 8090:8090 -t workshop-demo:latest`

To see the logs for the container:

`docker container logs workshop-demo`
