# rabbit-hole
Workspace for learning by experimenting with RabbitMQ messaging broker.

## Setup
Decided to use RabbitMQ server started in Docker container.
To follow my setup please install Docker client locally and run:

`docker run -d --hostname host-1 --name rabbit-1 -p 127.0.0.1:5672:5672 -p 127.0.0.1:15672:15672 rabbitmq:3-management`

This will download and run public RabbitMQ Docker image.

To reach RabbitMQ server which is run inside container, used `127.0.0.1:5672` address.

Besides `rabbit-server` (exposed on `5672` port) this specific Docker image brings also a management plugin installed and enabled by default.

To access it, simply enter `127.0.0.1:15672` in your browser and use default `guest/guest` credentials.

## Part 1 solution

When I created `Queue` bean then my RabbitMQ management panel displayed this queue and recognized it as legit Rabbit queue.

When I skipped `Queue` bean creation and used raw `template.convertAndSend(queueName, message)` method then RabbitMQ dashboard displayed incoming traffic but the traffic was like 'unrecognized' and not displayed in created queue statistics.