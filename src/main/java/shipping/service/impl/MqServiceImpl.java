package shipping.service.impl;

import com.jcabi.aspects.Loggable;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import shipping.service.api.MqService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
public class MqServiceImpl implements MqService {

    private static final Logger logger = Logger.getLogger(MqServiceImpl.class);

    @Override
    @Loggable
    public void sendMsg(String msg) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("shippingProject", false, false, false, null);
            channel.basicPublish("", "shippingProject", null, msg.getBytes(StandardCharsets.UTF_8));
        }
    }

}
