package hxari.maven.console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database
{
	
	private Connection connection;
	
	private String url;
	private int port = 3306;
	private String template = "jdbc:mysql://%s:%d/%s";
	private String hostname = "localhost";
	private String username = "root";
	private String password = "";
	private String database = "maven";
	
	public Database()
	{}
	
	public Database( String hostname, int port, String username, String password, String database )
	{
		this.port = port;
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.database = database;
	}
	
	public Connection get() throws Exception
	{
		if( this.connection == null )
		{
			this.open();
		}
		return( this ).connection;
	}
	
	public void close() throws SQLException
	{
		if( this.connection != null )
		{
			this.connection.close();
		}
	}
	
	public Connection open() throws SQLException
	{
		this.connection = DriverManager.getConnection(
			this.getUrl(),
			this.username,
			this.password
		);
		return( this ).connection;
	}
	
	public String getUrl()
	{
		if( this.url == null )
		{
			this.url = String.format(
				this.template,
				this.hostname,
				this.port,
				this.database
			);
		}
		return( this ).url;
	}
	
	public int getPort()
	{
		return( this ).port;
	}
	
	public String getHostname()
	{
		return( this ).hostname;
	}
	
	public String getUsername()
	{
		return( this ).username;
	}
	
	public String getPassword()
	{
		return( this ).password;
	}
	
	public String getDatabase()
	{
		return( this ).database;
	}
	
	public void setPort( int port )
	{
		this.port = port;
	}
	
	public void setHostname( String hostname )
	{
		this.hostname = hostname;
	}
	
	public void setUsername( String username )
	{
		this.username = username;
	}
	
	public void setPassword( String password )
	{
		this.password = password;
	}
	
	public void setDatabase( String database )
	{
		this.database = database;
	}
	
}
