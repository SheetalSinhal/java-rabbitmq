package myservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;

import mycommon.MyConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypackage.ConfigReader;
import mypackage.RabbitMQConnector;



public class PublishMessageServlet extends HttpServlet 
{
	Channel rabbitMQChannel;
	
	//@Override
	public void init(ServletConfig config) throws ServletException
	{
		System.out.println("My init method called.");
		super.init(config);
		
		try 
		{
			RabbitMQConnector rabbitMQConnector=new RabbitMQConnector();
			rabbitMQChannel = rabbitMQConnector.getChannel();
			rabbitMQConnector.initiateDirectSetUp();
		}
		catch(IOException e) 
		{
			System.out.println("IOException: while creating connection with RabbitMQ server: "+e.getMessage());			
		}
		catch(TimeoutException ex) 
		{
			System.out.println("TimeoutException: while creating connection with RabbitMQ server: "+ex.getMessage());
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	{
		String sendTo=request.getParameter("txtMessageTo");
		String message= request.getParameter("txtMessage");
		
		System.out.println("Message To: "+sendTo+"\t Message: "+message);
		
		try 
		{			
			String msgToBePublished = sendTo +",\n"+ message ;
			publishMessage(msgToBePublished);
			
			// Dispatch Request to success.jsp page with given message
			request.setAttribute(MyConstants.SUCCESS_MESSAGE_KEY, "Message published successfully.");			 
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("success.jsp");
			requestDispatcher.forward(request, response);
			
		}
		catch(IOException e) 
		{
			System.out.println("IOException: while creating connection with RabbitMQ server: "+e.getMessage());			
		}		
		catch(ServletException ex) 
		{
			System.out.println("ServletException : exception while dispatching request: "+ex.getMessage());
		}
		
	}
	
	private void publishMessage(String message) throws IOException 
	{
		ConfigReader configReader = ConfigReader.getConfigReader();
		String exchangeName = configReader.getConfigValue(MyConstants.DIRECT_EXCHANGE_NAME_KEY);
		String queueName = configReader.getConfigValue(MyConstants.DIRECT_QUEUE_NAME_KEY); 
		
		rabbitMQChannel.basicPublish(exchangeName, MyConstants.DIRECT_ROUTING_KEY, null, message.getBytes());		
	}

}
