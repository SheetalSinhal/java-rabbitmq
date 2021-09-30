package mypackage;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import mycommon.MyConstants;

import java.io.IOException;

public class RabbitMQConnector 
{
	private final String rabbitMQConnectionName="java app: try";
	Connection connection ;
	Channel channel;
	ConfigReader configReader;
	String rabbitMQHost, rabbitMQUserName, rabbitMQPassword;
	int rabbitMQPortNumber;
	
	
	public RabbitMQConnector() throws TimeoutException,IOException
	{
		//test
		configReader = ConfigReader.getConfigReader();
		rabbitMQHost=configReader.getConfigValue("rabbitMqServer");
		rabbitMQPortNumber=Integer.parseInt(configReader.getConfigValue("rabbitMqPortNumber"));		
		rabbitMQUserName=configReader.getConfigValue("rabbitMqUserName");
		rabbitMQPassword=configReader.getConfigValue("rabbitMqPassword");
		
		if(connection == null) 
			CreateRabbitMqConnection();	
			
		if(channel == null)
			getChannel();
	}
	
	
	private void CreateRabbitMqConnection() throws TimeoutException,IOException
	{		
		ConnectionFactory factory = new ConnectionFactory();		
		factory.setHost(rabbitMQHost);
		//factory.setPort(rabbitMQPortNumber);		
		factory.setUsername(rabbitMQUserName);
		factory.setPassword(rabbitMQPassword);	
		
		connection = factory.newConnection(rabbitMQConnectionName);	
		
		System.out.println("RabbitMQ Server connected successfully. ");
		
	}
	
	public Channel getChannel() throws TimeoutException,IOException
	{	if(channel == null)
			channel = connection.createChannel();
		return channel;
	}
	
	public void CloseResources() throws TimeoutException,IOException
	{
		channel.close();
		connection.close();
	} 
	
	public void initiateDirectSetUp() throws IOException
	{
		// declare a durable, non-autodelete exchange of "direct" type exchange
		String directExchangeName = configReader.getConfigValue(MyConstants.DIRECT_EXCHANGE_NAME_KEY);
		
		// check if exchange already exist 

		Exchange.DeclareOk exchangeStatus =	channel.exchangeDeclare(directExchangeName, "direct",true);
						
		// declare queue (durable,non-exclusive,not auto-deleted)
		String directQueueName = configReader.getConfigValue(MyConstants.DIRECT_QUEUE_NAME_KEY);
		Queue.DeclareOk queueStatus = channel.queueDeclare(directQueueName, true, false, false, null);		
		
		// bind queue and exchange
		String routingKey= MyConstants.DIRECT_ROUTING_KEY;
		channel.queueBind(directQueueName, directExchangeName, routingKey);
		
		System.out.println(directExchangeName+"and  "+directQueueName+" binded successfully.");		
	}

}
