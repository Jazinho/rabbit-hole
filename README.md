# rabbit-hole
Workspace for learning by experimenting with RabbitMQ messaging broker.

I'm following tutorials from [official RabbitMQ web page](https://www.rabbitmq.com/tutorials/tutorial-one-spring-amqp.html).

## Setup
Decided to use RabbitMQ server started in Docker container.
To follow my setup please install Docker client locally and run:

`docker run -d --hostname host-1 --name rabbit-1 -p 127.0.0.1:5672:5672 -p 127.0.0.1:15672:15672 rabbitmq:3-management`

This will download and run public RabbitMQ Docker image.

To reach RabbitMQ server which is run inside container, used `127.0.0.1:5672` address.

Besides `rabbit-server` (exposed on `5672` port) this specific Docker image brings also a management plugin installed and enabled by default.

To access it, simply enter `127.0.0.1:15672` in your browser and use default `guest/guest` credentials.

## :warning: Before you start :warning:

****

Before you start running programs related to below Parts, please make sure that your RabbitMQ instance queues are empty and does not contain any old data.

You can clean queue by going to management panel, then to section *Queues*, entering proper *queueName* and then at the bottom you have *Purge* sections where you can clear the queue from persisted messages.

## Part 1 solution - HelloWorld

When I created `Queue` bean then my RabbitMQ management panel displayed this queue and recognized it as legit Rabbit queue.

When I skipped `Queue` bean creation and used raw `template.convertAndSend(queueName, message)` method then RabbitMQ dashboard displayed incoming traffic but the traffic was like 'unrecognized' and not displayed in created queue statistics.

## Part 2 solution - Dispatching

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

### Fact 1
RabbitMQ has `DEFAULT_PREFETCH_SIZE` property set to **20**. This property sets a maximum number of messages that can be assigned to receiver. 
After reaching this limit, messages won't be dispatching any new messages to this receiver until it get unloaded.
Spring AMQP uses value **250**.

### Fact 2

In pure Java Rabbit MQ client (and others languages also) you always have to handle sending ACK or rejecting messages manually.
In Spring AMQP it is handled by framework, under the hood.

## Part 3 solution - Exchanges

:warning: **Senders never should send messages directly to the queues** :warning:

Sender should send it to exchanges and these should decide about sending (or not) messages forward.

Exchanges can have different types: 
- `direct` - messages routed by *routing key* (adres docelowych kolejek)
- `topic` - messages routed based on wildard matching between routin key and queues binding routing pattern
- `headers` - uses argument with headers to route messages
- `fanout` - duplicates messages and routes to every connected queue

**AnonymousQueue** - by creating `new AnonymousQueue()` we have a randomly-named queue created. It also creates a new, empty queue every time we start an applicaiton and deletes itself (and hence messages also) after stopping the app.

Anonymous queues names are generated and look like this `spring.gen-zfbuvu8MSW-fPjkiLmj_cg`.

If we don't bind any queue to an existing exchange, then the messages incoming to the exchange will be lost and deleted (as no consumer is listening).

To send a message to the exchange we need to use `.convertAndSend(exchangeName, routingKey, message)` method. But as for fanout exchanges the routing key doesn't matter then we can use dummy empty string for 2nd param.

:warning: To observe this solution fully, you have to monitor your exchange and queues LIVE, during program being started and traffic generated. After program stop, queues are deleted and messages purged. :warning: