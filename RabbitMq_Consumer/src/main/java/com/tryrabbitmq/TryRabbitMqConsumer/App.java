package com.tryrabbitmq.TryRabbitMqConsumer;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException 
    {
        System.out.println( "Hello All!" );        
        
        RabbitMqConnectionCreator connectionCreator = new RabbitMqConnectionCreator();
        Channel channel = connectionCreator.getChannel();
        // Create Queue 
        //String queueName = ConfigReader.getConfigReader().getPropertyValue(MyConstants.RABBITMQ_DIRECT_QUEUE_NAME_KEY);
        String queueName= MyConstants.RABBITMQ_DIRECT_QUEUE_NAME;
        channel.queueDeclare(queueName, true, false, false, null);
        
        DirectQueueConsumer directQueueConsumer=new DirectQueueConsumer(channel);
        directQueueConsumer.InitiateConsumer(queueName);
        
    }
    
    
}
