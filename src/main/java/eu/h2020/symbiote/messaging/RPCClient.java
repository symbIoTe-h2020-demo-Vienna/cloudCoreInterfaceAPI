package eu.h2020.symbiote.messaging;

import com.rabbitmq.client.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by mateuszl on 03.10.2016.
 */
public class RPCClient {

    private static RPCClient rpcClient;

    public static RPCClient getInstance() throws Exception {
        synchronized (RPCClient.class){
        if (rpcClient==null){
            rpcClient = new RPCClient();
        }}
        return rpcClient;
    }

    private static Log log = LogFactory.getLog(RPCClient.class);

    private Connection connection;
    private Channel channel;
    private String RPC_REQUEST_QUEUE_NAME = "rpc_request_queue";
    private String RPC_REPLY_QUEUE_NAME = "rpc_reply_queue";
    private QueueingConsumer consumer;

    private RPCClient() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(RPC_REPLY_QUEUE_NAME, true, consumer);
    }

    public String rpcCall(String message) throws Exception {
        String response = "";
        String corrId = java.util.UUID.randomUUID().toString();

        channel.queueDeclare(RPC_REQUEST_QUEUE_NAME,false,false,false,null);
        channel.queueDeclare(RPC_REPLY_QUEUE_NAME,false,false,false,null);

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(RPC_REPLY_QUEUE_NAME)
                .build();

        log.info("[] RPC call - sending message and waiting for answer...\n");
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
        log.info("RPC connection closed!");
    }
}
