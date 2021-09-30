package com.tryrabbitmq.TryRabbitMqConsumer;

import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;

public class ConfigReader 
{
	private Properties propertyReader;
	private static  ConfigReader configReader;
	
	private ConfigReader() 
	{
		LoadConfigFile();
	}
	
	public static ConfigReader getConfigReader() 
	{
		if(configReader == null)
			configReader = new ConfigReader();
		return configReader;
	}
	
	private void LoadConfigFile() 
	{		
		InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("configurations.properties");
		propertyReader = new Properties();
		try {
		propertyReader.load(fis);		
		}
		catch(IOException ex) 
		{
			System.out.print("IOException: while loading/closing property file: "+ex.getMessage());
		}
		
	}
	
	public String getPropertyValue(String propertyName) 
	{
		return propertyReader.getProperty(propertyName);
	}

}
