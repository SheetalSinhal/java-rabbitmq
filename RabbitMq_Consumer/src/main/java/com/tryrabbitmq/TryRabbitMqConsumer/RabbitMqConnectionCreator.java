package com.tryrabbitmq.TryRabbitMqConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqConnectionCreator 
{
	private static final String CONNECTION_NAME="java app : consumer";
	private static Connection connection;
	private Channel channel;
	
	
	public RabbitMqConnectionCreator()
	{
		try {	
		
		if(connection == null)
			CreateConnection();
			
		if(channel == null)
			channel = connection.createChannel();
		}
		catch(IOException ex) 
		{
			System.out.println("IOException: while establishing connection with RabbitMq: "+ex.getMessage());
		}		
	}
	
	public static void CreateConnection()
	{
		try {
		// Get Localhost, username, password
//		ConfigReader configReader = ConfigReader.getConfigReader();
//		String rabbitmqHost=configReader.getPropertyValue(MyConstants.RABBITMQ_HOST_KEY);
//		String rabbitmqUser=configReader.getPropertyValue(MyConstants.RABBITMQ_USER_KEY);
//		String rabbitmqPassword=configReader.getPropertyValue(MyConstants.RABBITMQ_PASSWORD_KEY);
		
		// Create Connection Factory object
		ConnectionFactory  factory = new ConnectionFactory(); 
		factory.setHost(MyConstants.RABBITMQ_HOST);
		factory.setUsername(MyConstants.RABBITMQ_USER);
		factory.setPassword(MyConstants.RABBITMQ_PASSWORD);
		
		connection = factory.newConnection(CONNECTION_NAME);
		}
		catch(IOException ex) 
		{
			System.out.println("IOException: while establishing connection with RabbitMq: "+ex.getMessage());
		}
		catch(TimeoutException ex) 
		{
			System.out.println("TimeoutException: while establishing connection with RabbitMq: "+ex.getMessage());
		}		
	}
	
	public Channel getChannel() 
	{
		return channel;		
	}

}
