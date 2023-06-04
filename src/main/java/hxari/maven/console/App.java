package hxari.maven.console;

import hxari.maven.console.TaskManager;

public class App 
{
	
	public static TaskManager tm;
	
	public static void main( String[] args )
	{
		tm = new TaskManager();
		tm.start();
	}
	
}