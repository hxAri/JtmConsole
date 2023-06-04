package hxari.maven.console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TaskManager
{
	
	private List<Task> tasks = new ArrayList<>();
	private Scanner input = new Scanner( System.in );
	private Database driver = new Database();
	private Connection mysql;
	
	public TaskManager()
	{}
	
	public static void clear()
	{
		System.out.print( "\033[H\033[2J" );
		System.out.flush();
	}
	
	public void close()
	{
		try
		{
			this.driver.close();
			this.clear();
			System.out.println( "Finish" );
			System.exit( 0 );
		}
		catch( SQLException e )
		{
			System.out.println( "\n" + e.getMessage() );
			System.out.print( "Try again [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) &&
				next.equals( "" ) )
			{
				this.close();
			}
		}
	}
	
	public void createTask()
	{
		this.createTask( "", "" );
	}
	
	public void createTask( String name, String description )
	{
		this.clear();
		try
		{
			System.out.println( "Create new task" );
			if( name == "" )
			{
				System.out.print( "Task Name (Max 20) " );
				name = this.input.nextLine();
				if( name == "" ) throw new Exception( "Task name can't be empty" );
			}
			if( description == "" )
			{
				System.out.print( "Task Description (Max 250) " );
				description = this.input.nextLine();
				if( description == "" ) throw new Exception( "Task must be have description" );
			}
			try
			{
				PreparedStatement stmt;
				stmt = this.mysql.prepareStatement( "INSERT INTO tasks( name, description ) VALUES( ?, ? )" );
				stmt.setString( 1, name );
				stmt.setString( 2, description );
				
				if( stmt.executeUpdate() > 0 )
				{
					System.out.println( "Task created successfully" );
					System.out.print( "Create new task again [Y/n] " );
					String next = this.input.nextLine();
					if( next.equals( "Y" ) ||
						next.equals( "" ) )
					{
						this.createTask();
					}
				}
				else {
					throw new Exception( "Failed create task \"" + name + "\"" );
				}
			}
			catch( SQLException e )
			{
				name = "";
				description = "";
				
				throw e;
			}
		}
		catch( Exception e )
		{
			System.out.println( "\n" + e.getMessage() );
			System.out.print( "Try again [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				this.createTask( name, description );
			}
		}
	}
	
	public void listTask()
	{
		this.clear();
		try
		{
			this.printTask();
			System.out.print( "\nSelect Task 1-" + this.tasks.size() + " " );
			Integer choice = Integer.parseInt( this.input.nextLine() );
			Task task = this.tasks.get( choice > 0 ? choice -1 : choice );
			System.out.println( "\n1. Update" );
			System.out.println( "2. Delete" );
			System.out.println( "3. Back" );
			System.out.print( "\nSelect Action 1-3 " );
			Integer action = Integer.parseInt( this.input.nextLine() );
			switch( action )
			{
				case 1:
					this.updateTask( task );
					break;
				case 2:
					this.deleteTask( task.getId() );
					break;
				case 3:
					this.listTask();
					break;
				default:
					throw new Exception( "Index out of range" );
			}
		}
		catch( Exception e )
		{
			System.out.println( "\n" + e.getMessage() );
			System.out.print( "Try again [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				this.listTask();
			}
		}
	}
	
	public void deleteTask()
	{
		this.clear();
		try
		{
			this.printTask();
			System.out.print( "\nDelete Task " + this.tasks.size() + " " );
			Integer choice = Integer.parseInt( this.input.nextLine() );
			Task task = this.tasks.get( choice > 0 ? choice -1 : choice );
			this.deleteTask(
				task.getId()
			);
		}
		catch( Exception e )
		{
			System.out.println( "\n" + e.getMessage() );
			System.out.print( "Try again [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				this.deleteTask();
			}
		}
	}
	
	public void deleteTask( int id ) throws Exception
	{
		try
		{
			PreparedStatement stmt = this.mysql.prepareStatement( "DELETE FROM tasks WHERE id = ?" );
			stmt.setInt( 1, id );
			
			if( stmt.executeUpdate() > 0 )
			{
				System.out.println( "Task deleted successfully" );
				System.out.print( "Enter " );
				this.input.nextLine();
			}
			else {
				throw new Exception( "Failed to delete task \"" + id + "\"" );
			}
		}
		catch( SQLException e )
		{
			System.out.println( "\n" + e.getMessage() );
			System.out.print( "Try again [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				this.deleteTask();
			}
		}
	}
	
	public void printTask() throws Exception
	{
		this.tasks.clear();
		
		Statement stmt = this.mysql.createStatement();
		ResultSet result = stmt.executeQuery( "SELECT * FROM tasks" );
		
		while( result.next() )
		{
			this.tasks.add( new Task(
				result.getInt( "id" ),
				result.getString( "name" ),
				result.getString( "description" )
			));
		}
		
		if( this.tasks.isEmpty() )
		{
			System.out.println( "There is no task" );
			System.out.print( "Do you want to create a new task? [Y/n]" );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				this.createTask();
			}
			return;
		}
		
		System.out.println( "List available tasks\n" );
		
		for( int i = 0; i < this.tasks.size(); i++ )
		{
			String spaces = 1 + "  ";
			spaces = " ".repeat( spaces.length() );
			System.out.println( String.format( "%d. %s\n%s%s",
				i +1,
				this.tasks.get( i ).getName(),
				spaces,
				this.tasks.get( i ).getDescription()
			));
		}
	}
	
	public void updateTask()
	{
		this.clear();
		try
		{
			this.printTask();
			System.out.print( "\nUpdate Task " + this.tasks.size() + " " );
			Integer choice = Integer.parseInt( this.input.nextLine() );
			this.updateTask( this.tasks.get( choice > 0 ? choice -1 : choice ) );
		}
		catch( Exception e )
		{
			System.out.println( "\n" + e.getMessage() );
			System.out.print( "Try again [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				this.updateTask();
			}
		}
	}
	
	public void updateTask( Task task )
	{
		this.clear();
		try
		{
			String name = task.getName();
			String description = task.getDescription();
			System.out.print( "Update Task name [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				System.out.print( "New Task name: " );
				name = this.input.nextLine();
				if( name.length() <= 0 )
				{
					name = task.getName();
				}
			}
			System.out.print( "Update Task description [Y/n] " );
			next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				System.out.print( "New Task description: " );
				description = this.input.nextLine();
				if( description.length() <= 0 )
				{
					description = task.getDescription();
				}
			}
			if( name != task.getName() ||
				description != task.getDescription() )
			{
				this.updateTask(
					task.getId(),
					name,
					description
				);
			}
			else {
				System.out.println( "There are no need to update" );
				System.out.print( "Enter " );
				this.input.nextLine();
			}
		}
		catch( Exception e )
		{
			System.out.println( "\n" + e.getMessage() );
			System.out.print( "Try again [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				this.updateTask();
			}
		}
	}
	
	public void updateTask( int id, String name, String description ) throws Exception
	{
		try
		{
			PreparedStatement stmt = this.mysql.prepareStatement( "UPDATE tasks SET name = ?, description = ? WHERE id = ?" );
			stmt.setString( 1, name );
			stmt.setString( 2, description );
			stmt.setInt( 3, id );
			
			if( stmt.executeUpdate() > 0 )
			{
				System.out.println( "Task updated successfully" );
				System.out.print( "Enter " );
				this.input.nextLine();
			}
			else {
				throw new Exception( "Failed to update task \"" + id + "\"" );
			}
		}
		catch( SQLException e )
		{
			System.out.println( "\n" + e.getMessage() );
			System.out.print( "Try again [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) ||
				next.equals( "" ) )
			{
				this.updateTask();
			}
		}
	}
	
	public void printMenu()
	{
		System.out.println( "1. Create Task" );
		System.out.println( "2. Edit Task" );
		System.out.println( "3. Delete Task" );
		System.out.println( "4. List Tasks" );
		System.out.println( "5. Quit" );
		System.out.print( "\nChoice " );
	}
	
	public void start()
	{
		try
		{
			this.mysql = this.driver.open();
			while( true )
			{
				this.clear();
				this.printMenu();
				try
				{
					int choice = Integer.parseInt( this.input.nextLine() );
					switch( choice )
					{
						case 1:
							this.createTask();
							break;
						case 2:
							this.updateTask();
							break;
						case 3:
							this.deleteTask();
							break;
						case 4:
							this.listTask();
							break;
						case 5:
							this.close();
							return;
						default:
							throw new Exception( "Input out of range" );
					}
				}
				catch( Exception e )
				{
					System.out.println( "\n" + e.getMessage() );
					System.out.print( "Try again [Y/n] " );
					String next = this.input.nextLine();
					if( next.equals( "Y" ) == false &&
						next.equals( "" ) == false )
					{
						this.close();
						break;
					}
				}
			}
		}
		catch( SQLException e )
		{
			System.out.println( "Database connection error" );
			System.out.println( "\n" + e.getMessage() );
		}
		finally {
			System.out.print( "Try again [Y/n] " );
			String next = this.input.nextLine();
			if( next.equals( "Y" ) == false &&
				next.equals( "" ) == false )
			{
				this.close();
			}
			return;
		}
	}
	
}