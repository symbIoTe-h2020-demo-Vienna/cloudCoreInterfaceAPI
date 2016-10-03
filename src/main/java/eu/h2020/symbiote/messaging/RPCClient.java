package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.*;

/**
 * Created by mateuszl on 03.10.2016.
 */
public class RPCClient {

    private Connection connection;
    private Channel channel;
    private String RPC_REQUEST_QUEUE_NAME = "rpc_request_queue";
    private String RPC_REPLY_QUEUE_NAME = "rpc_reply_queue";
    private QueueingConsumer consumer;

    public RPCClient() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(RPC_REPLY_QUEUE_NAME, true, consumer);
    }

    public String rpcCall(String message) throws Exception {
        String response = null;
        String corrId = java.util.UUID.randomUUID().toString();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(RPC_REPLY_QUEUE_NAME)
                .build();

        System.out.println(" [] RPC call sending message and waiting for answer.........\n");
        channel.basicPublish("", RPC_REQUEST_QUEUE_NAME, props, message.getBytes());
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response = new String(delivery.getBody());
                break;
            }
        }
        return response;
    }

    public void close() throws Exception {
        connection.close();
    }
}
