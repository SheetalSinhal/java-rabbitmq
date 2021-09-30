package com.tryrabbitmq.TryRabbitMqConsumer;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class DirectQueueConsumer
{
	Channel channel;
	
	public DirectQueueConsumer(Channel channel) 
	{
		this.channel = channel;
	}
	
	
	public void InitiateConsumer(String queueName) throws IOException 
	{
		DefaultConsumer consumer = new DefaultConsumer(channel) 
        {
        	public void handleDelivery(String consumerTag,
                    Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException 
        	{
        			String message = new String(body, "UTF-8");
        			System.out.println(" [x] Received: '" + message + "'");
        			// acknowledge server
        	        channel.basicAck(envelope.getDeliveryTag(), true);
        	}        	
        };
        
        String consumerTag="java-consumer-1";
        boolean autoAcknowledgeServer = false;
        // Starting consumer
        channel.basicConsume(queueName, autoAcknowledgeServer, consumerTag,consumer);   
	}
	
	

}
