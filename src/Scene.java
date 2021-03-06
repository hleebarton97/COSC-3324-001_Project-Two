/**
 * Scene Class
 * The Scene class keeps track of the player's position (node)
 * and the direction they are facing (dir).
 * This class also loads the images based on the player's position
 * and direction and is used to update them into the game.
 * fields:
 * 	btn_Use: A JButton that changes the text that is displayed to the Player.
 * 	dir: An integer value between 0 and 3 that determines the direction the Player is facing.
 * 	info: An Information object that is used to store and display information about each scene.
 * 	node: An integer value between 0 and 10 that represents which scene the player is currently on.
 * 	rooms: An ArrayList of Room objects to hold the 8 rooms that the player can navigate through.
 * 	scene: A Scene object to be displayed to the Player.
 * 	type: An integer value that indicates the type of information being displayed.
 * Scene Class uses the src/assets/img file to get images for the Room objects.
 * Date: 02 December 2017
 * OOP Project 2: The Mansions
 * @author Team 7
 * @version 1.0
 * @since 1.0
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextPane;

public class Scene 
{
	private int node;			 	// The digit value of the scene the player is currently on (0 - 10).
	private int dir;			 	// The direction the player is facing at node position (0 - 3).
	private int type; 				// Handles different information at the same node and direction.
	private BufferedImage scene; 	// Actual image of the scene.
	
	private Information info;	 	// The information provided for each scene.
	private JButton btn_Use;		// changes the text that is displayed to the Player
	
	private ArrayList<Room> rooms; 	// ArrayList of the 8 rooms.
	
	/**
	 * This constructor doesn't take a node value and sets the scene
	 * at the start.
	 * @param windowText A reference to the JTextPane. The String value
	 * 			in it will be used to create an Information object.
	 * @param btn_Use A reference to the JButton to be copied into the
	 * 			btn_Use field.
	 */
	public Scene(JTextPane windowText, JButton btn_Use)
	{
		this.setNode(0); 		 		// Default node position at 0.
		this.setDirection(0);	 		// Default direction at 0.
		this.setType(0);				// Default type of information.
		this.info = new Information(windowText); // Create information object.
		this.btn_Use = btn_Use; // Set button to change text.
		this.createRooms(); // Create room objects.
	}
	
	/**
	 * This constructor takes in a node value and sets the scene.
	 * @param node Value that represents which scene the player is on.
	 * 			store the value in the node field.
	 * @param dir Value that represents the direction the player is facing.
	 * 			store it in the dir field.
	 * @param windowText A reference to the JTextPane. The String value
	 * 			in it will be used to create an Information object.
	 * @param btn_Use A reference to the JButton to be copied into the
	 * 			btn_Use field.
	 */
	public Scene(int node, int dir, JTextPane windowText, JButton btn_Use)
	{
		this.setNode(node);				// Set node position.
		this.setDirection(dir);			// Set dir.
		this.setType(0);				// Set type.
		this.info = new Information(windowText); // Create information object.
		this.btn_Use = btn_Use;			// Set button to change text.
		this.createRooms(); // Create room objects.
	}

	
	// Getters
	/**
	 * Get the current value of the node.
	 * @return value of node.
	 */
	public int getNode()
	{
		return this.node;
	}
	
	/**
	 * Get the current direction the player is facing.
	 * @return value of dir.
	 */
	public int getDirection()
	{
		return this.dir;
	}
	
	/**
	 * Get the current type of information that is being displayed.
	 * @return the integer value used to determine the type of information
	 * 			being displayed.
	 */
	public int getType()
	{
		return this.type;
	}
	
	/**
	 * Get the current scene image.
	 * @return image of the scene.
	 */
	public BufferedImage getScene()
	{
		return this.scene;
	}
	
	/**
	 * Get the information of the scene.
	 * @return A reference to the Information object.
	 */
	public Information getInfo()
	{
		return this.info;
	}
	
	/**
	 * Get the use item button.
	 * @return A reference to the use item JButton
	 */
	public JButton getButton()
	{
		return this.btn_Use;
	}

	
// Setters
	/**
	 * Set the value of node.
	 * @param node -- Value of scene that the player is on.
	 */
	public void setNode(int node)
	{
		this.node = node;
		this.loadImage();
	}
	
	/**
	 * Set the value of dir.
	 * @param dir --  Value of direction that the player is facing at current node.
	 */
	public void setDirection(int dir)
	{
		this.dir = dir;
		this.loadImage();
	}
	
	/**
	 * Set the value of type.
	 * @param type The type of information
	 */
	public void setType(int type)
	{
		this.type = type;
	}
	
	/**
	 * Update player's current direction and position.
	 * @param node The scene the player is currently on.
	 * @param dir The direction the player is facing.
	 */
	public void updateScene(int node, int dir)
	{
		this.node = node;
		this.dir = dir;
		this.setInformation(node, dir);
		this.updateRoom(node);
		this.loadImage();
	}

// Functions
	/**
	 * The setInfomation method uses the node and player direction values to
	 * determine the appropriate information that should be set for the Room
	 * objects.
	 * @param node Value that represents which scene the player is on.
	 * 			Used to determine the type of information to be set on a Room.
	 * @param dir Value that represents the direction the player is facing.
	 * 			Used to determine the type of information to be set on a Room.
	 */
	public void setInformation(int node, int dir)
	{
		if(node == 1) // Check NODE 1 type.
		{
			if(this.dir == 0 && this.getRoomByName("Hallway").hasEntered())
				this.setType(1);
			else if(this.dir == 1 && this.getRoomByName("Lounge").hasEntered())
				this.setType(1);
			else if(this.dir == 2 && this.getRoomByName("Study").hasEntered()) // Study is unlocked.
				this.setType(1);
			else
				this.setType(0);	
		}
		else if(node == 2) // Check Lounge.
		{
			if(this.getRoomByName("Lounge").getItem().isPickedUp())
				this.setType(1);
			else
				this.setType(0);
		}
		else if(node == 3) // Check NODE 3 type.
		{
			if(this.dir == 1 && this.getRoomByName("Billiard Room").hasEntered())
				this.setType(1);
			else if(this.dir == 2 && this.getRoomByName("Ballroom").hasEntered())
				this.setType(1);
			else
				this.setType(0);
		}
		else if(node == 4) // Check Study.
		{
			if(this.getRoomByName("Study").getItem().isPickedUp())
				this.setType(1);
			else
				this.setType(0);
		}
		else if(node == 5) // Check Ballroom.
		{
			if(this.getRoomByName("Ballroom").getItem().isPickedUp())
				this.setType(1);
			else
				this.setType(0);
		}
		else if(node == 6) // Check NODE 6 type.
		{
			if(this.dir == 0 && this.getRoomByName("Conservatory").hasEntered())
				this.setType(1);
			else if(this.dir == 1 && this.getRoomByName("Library").hasEntered())
				this.setType(1);
			else if(this.dir == 2 && this.getRoomByName("Kitchen").hasEntered())
				this.setType(1);
			else
				this.setType(0);
		}
		else
			this.setType(0);
		
		this.info.updateInformation(node, dir, this.type);
	}
	
	/**
	 * The getRoomByName method searches the ArrayList of Room objects
	 * to find a Room whose value in its name field matches the name parameter.
	 * @param name The name of the Room to be searched for.
	 * @return The Room object whose name field matches the value in the
	 * 			name parameter. Otherwise, null.
	 */
	public Room getRoomByName(String name)
	{
		for(Room room : this.rooms)
		{
			if(room.getName().equals(name))
				return room;
		}
		return null;
	}
	
	/**
	 * The getRoomByPosition method uses the node value to
	 * determine which Room object to return
	 * @return The Room object whose node value is indicated below.
	 */
	public Room getRoomByPosition()
	{
		if(this.node == 2)
			return this.getRoomByName("Lounge");
		else if(this.node == 4)
			return this.getRoomByName("Study");
		else if(this.node == 5)
			return this.getRoomByName("Ballroom");
		else if(this.node == 7)
			return this.getRoomByName("Billiard Room");
		else if(this.node == 8)
			return this.getRoomByName("Library");
		else if(this.node == 9)
			return this.getRoomByName("Conservatory");
		else if(this.node == 10)
			return this.getRoomByName("Kitchen");
		else
			return this.getRoomByName("Hallway");
	}
	
	/**
	 * The updateRoom method uses the node value to determine
	 * if a player has entered a room. If so, then we call
	 * setHasEntered method and pass it true to indicate
	 * that the player has entered the room.
	 * @param node The value that indicates the Player's current scene.
	 */
	public void updateRoom(int node)
	{
		if(node == 1)
			this.getRoomByName("Hallway").setHasEntered(true);
		else if(node == 2)
			this.getRoomByName("Lounge").setHasEntered(true);
		else if(node == 4)
			this.getRoomByName("Study").setHasEntered(true);
		else if(node == 5)
			this.getRoomByName("Ballroom").setHasEntered(true);
		else if(node == 7)
			this.getRoomByName("Billiard Room").setHasEntered(true);
		else if(node == 8)
			this.getRoomByName("Library").setHasEntered(true);
		else if(node == 9)
			this.getRoomByName("Conservatory").setHasEntered(true);
		else if(node == 10)
			this.getRoomByName("Kitchen").setHasEntered(true);
		
	}
	
	/**
	 * The createRooms method creates the rooms and adds the Items to the rooms.
	 */
	public void createRooms()
	{
		rooms = new ArrayList<Room>();	// Initialize rooms ArrayList.
		
		Room lounge = new Room("Lounge");
		Room billiard = new Room("Billiard Room", true); 	// Locked
		Room library = new Room("Library", true); 			// Locked
		Room study = new Room("Study", true); 				// Locked
		Room ballroom = new Room("Ballroom", true);			// Locked
		Room kitchen = new Room("Kitchen", true);			// Locked
		Room conservatory = new Room("Conservatory", true); // Locked
		Room hallway = new Room("Hallway");
		
		/**
		 * Add rooms to ArrayList and add items to each room.
		 */
		this.rooms.add(lounge);
		this.getRoomByName("Lounge").setItemName("Key");
		this.getRoomByName("Lounge").setItemType(4);
		
		this.rooms.add(billiard);
		this.getRoomByName("Billiard Room").setItemName("Candle");
		this.getRoomByName("Billiard Room").setItemType(8);
		
		this.rooms.add(library);
		this.getRoomByName("Library").setItemName("Button");
		this.getRoomByName("Library").setItemType(10);
		
		this.rooms.add(study);
		this.getRoomByName("Study").setItemName("Note");
		this.getRoomByName("Study").setItemType(5);
		
		this.rooms.add(ballroom);
		this.getRoomByName("Ballroom").setItemName("Fancy Key");
		this.getRoomByName("Ballroom").setItemType(7);
		
		this.rooms.add(kitchen);
		this.getRoomByName("Kitchen").setItemName("Master Key");
		this.getRoomByName("Kitchen").setItemType(9);
		
		this.rooms.add(conservatory);
		this.getRoomByName("Conservatory").setItemName("Lever");
		this.getRoomByName("Conservatory").setItemType(0);
		
		this.rooms.add(hallway);
		this.getRoomByName("Hallway").setItem(null);
		
	}
	
	/**
	 * The printNodeDir method displays the Players position (scene value)
	 * and direction.
	 */
	public void printNodeDir()
	{
		System.out.println("Position: " + this.node + ", Dir: " + this.dir);
	}
	
	/**
	 * The loadImage method uses the src/assets/img file to load
	 * an image and set the scene to the loaded image based on 
	 * the player's position (this.node).
	 */
	private void loadImage()
	{
		if(this.node == 0) // START.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_0_Mansion.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 1 && this.dir == 0) // Hallway facing forward.
		{
			try 
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_1_0_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		if(this.node == 1 && this.dir == 1) // Hallway facing left (door of Lounge).
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_1_1_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		if(this.node == 1 && this.dir == 2) // Hallway facing right (door of Study).
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_1_2_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		if(this.node == 1 && this.dir == 3) // Hallway facing entrance.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_1_3_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 2) // THE LOUNGE.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_2_Lounge.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		if(this.node == 3 && this.dir == 0) // Hallway middle facing forward.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_3_0_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		if(this.node == 3 && this.dir == 1) // Hallway middle facing left (door of Billiard Room).
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_3_1_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		if(this.node == 3 && this.dir == 2) // Hallway middle facing right (door of Ballroom).
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_3_2_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		if(this.node == 3 && this.dir == 3) // Hallway middle facing entrance.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_3_3_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 4) // THE STUDY.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_4_Study.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 5) // THE BALLROOM.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_5_Ballroom.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 6 && this.dir == 0) // Hallway end facing forward (door to Conservatory).
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_6_0_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 6 && this.dir == 1) // Hallway end facing left. (door to Library).
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_6_1_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 6 && this.dir == 2) // Hallway end facing right. (door to Kitchen).
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_6_2_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 6 && this.dir == 3) // Hallway end facing entrance.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_6_3_Hallway.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 7) // BILLIARD ROOM.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_7_Billiard.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 8) // LIBRARY.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_8_Library.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 9) // CONSERVATORY.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_9_Conservatory.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
		else if(this.node == 10) // KITCHEN.
		{
			try
			{
				this.scene = ImageIO.read(new File("src/assets/img", "node_10_Kitchen.png"));
				this.printNodeDir();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("Error loading image [scene " + this.node + "]");
			}
		}
	}
}
