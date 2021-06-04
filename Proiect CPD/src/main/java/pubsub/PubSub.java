package pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class PubSub {

    private static ConnectionFactory factory = new ConnectionFactory();;
    private static final Scanner sc = new Scanner(System.in);

    public static void publish(String node, String topic) {
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(topic, false, false, false, null);
            String message;
            while(true) {
                message = sc.nextLine();
                channel.basicPublish("", topic, null, message.getBytes());
                System.out.println(" [" + node + " - " + topic + "] Sent '" + message + "'");
            }
        } catch (TimeoutException | IOException timeoutException) {
            timeoutException.printStackTrace();
        }
    }

    public static void subscribe(String node, String topic) {
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(topic, false, false, false, null);
            //System.out.println(" [" + node + "] Waiting for messages for topic: " + topic);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [" + node + " - " + topic + "] Received: " + message + "'");
            };
            channel.basicConsume(topic, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
