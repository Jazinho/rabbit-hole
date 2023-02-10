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

## Part 1 solution - HelloWorld

When I created `Queue` bean then my RabbitMQ management panel displayed this queue and recognized it as legit Rabbit queue.

When I skipped `Queue` bean creation and used raw `template.convertAndSend(queueName, message)` method then RabbitMQ dashboard displayed incoming traffic but the traffic was like 'unrecognized' and not displayed in created queue statistics.

## Part 2 solution - dispatching

By default RabbitMQ uses **round-robin** technique of distributing load. It means every new message will be sent to the next receiver in order.
This will cause every receiver will handle the same number od messages (no matter of messages load).

However, Spring AMQP uses **fair dispatching** technique by default.
That means, on message dispatch moment, framework will check the number of messages waiting for being processed by each receiver (*unACKed messages*) and will dispatch a message to the receiver with the lowest number of such *unACKed messages*.
This will cause that receivers will be loaded equally, depending on the amount of work which need to be spent to handle messages. 

In Part 2 solution we created:
* 1 sender (which sends messages in following manner):
  * first message will need 1 second of processing
  * second message will need 4 seconds of processing
  * third message will need 7 seconds of processing
  * and then cycles repeats...
* 1 queue
* 3 receivers