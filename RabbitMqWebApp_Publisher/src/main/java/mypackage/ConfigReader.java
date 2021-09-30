package mypackage;

import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfigReader 
{
	private  String configFileName="Configuration.properties";
	private  Properties config;	
	private static ConfigReader configReader;
	
	public static ConfigReader getConfigReader() 
	{
		if(configReader == null)
			configReader = new ConfigReader();
		return configReader;
	}
	
	private ConfigReader()
	{		
		LoadConfigFile();
	}
			
	private  void LoadConfigFile() 
	{		
		config = new Properties();
		try 
		{
		InputStream fis= getClass().getClassLoader().getResourceAsStream(configFileName);
		config.load(fis);
		System.out.println(config);
		}
		catch(IOException ex) 
		{
			System.out.println("IOException: while loading "+configFileName+" "+ex.getMessage());
		}	
	}
	
	public String getConfigValue(String configName) 
	{
		return config.getProperty(configName);
	}

}
