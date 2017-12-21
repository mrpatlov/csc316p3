import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main entry class for social network program.
 *Took contributors names out for identity purposes
 */
public class SocialNetwork {
	
	Network myNetwork;
	
	/**
	 * Constructor for SocialNetwork objects
	 */
	public SocialNetwork(){
		myNetwork = new Network();
	}

	/**
	 * Main method. Requires a single command line argument: the input filename containing graph structure
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("SocialNetwork: Proper Usage: java SocialNetwork inputfile");
			System.exit(0);
		}
		SocialNetwork myNetwork = new SocialNetwork();
		File input = new File(args[0]);
		try{
		myNetwork.readFile(input);
		}
		catch(Warning e){
			
		}
		System.out.println("$");
		Scanner console = new Scanner(System.in);
		while (console.hasNextLine()){
			try{
				myNetwork.processCommand(console.nextLine());
			}
			catch (Warning e){
				System.out.println(e.getMessage());
			}
		}
		console.close();

	}

	/**
	 * verifies input and calls the appropriate methods to handle it.
	 * @param nextLine the line of input containing the command as well as the arguments separated by whitespace
	 * @throws Warning
	 */
	private void processCommand(String nextLine) throws Warning{
		Scanner input = new Scanner(nextLine);
		String command = "";
		String person = "";
		String friend = "";
		
		//get command
		if (input.hasNext()){
			command = input.next();
		}
		else {
			input.close();
			return;
		//	throw new Warning("No Command Found");
		}
		
		//process command
		if (command.equals("notconnected")){
			int notconnected = myNetwork.notConnected2();
			System.out.println(notconnected);
		}
		else if (command.equals("isfriend")){
			if (input.hasNext()){
				person = input.next();
			}
			else{
				input.close();
				throw new Warning("isfriend needs two arguments");
			}
			if (input.hasNext()){
				friend = input.next();
			}
			else{
				input.close();
				throw new Warning("isfriend needs two arguments");
			}
			if (myNetwork.isConnected(person, friend)){
				System.out.println("yes");
			}
			else{
				System.out.println("no");
			}
			
		}
		else if ( command.equalsIgnoreCase("mutual")){
			if (input.hasNext()){
				person = input.next();
			}
			else{
				input.close();
				throw new Warning("mutual needs two arguments");
			}
			if (input.hasNext()){
				friend = input.next();
			}
			else{
				input.close();
				throw new Warning("mutual needs two arguments");
			}
			EdgeList mutual = myNetwork.mutualConnections(person, friend);
			while (mutual.hasNext()){
				System.out.println(mutual.next());
			}
		}
		else if ( command.equalsIgnoreCase("popular")){
			EdgeList mostPopular = myNetwork.mostPopular();
			while (mostPopular.hasNext()){
				System.out.println(mostPopular.next());
			}
			
		}
		else if ( command.equalsIgnoreCase("relation")){
			if (input.hasNext()){
				person = input.next();
			}
			else{
				input.close();
				throw new Warning("relation needs two arguments");
			}
			if (input.hasNext()){
				friend = input.next();
			}
			else{
				input.close();
				throw new Warning("relation needs two arguments");
			}
			EdgeList path = myNetwork.shortestPath(person, friend);
			if (path != null) {
				while (path.hasNext()){
					System.out.println(path.next());
				}
			}
		}
		else{
			input.close();
			throw new Warning("Invalid command");
		}
		System.out.println("$");
		input.close();
	}

	/**
	 * reads the structure file provided at program initialization and fills the graph
	 * @param input the filename containing the graph structure
	 * @throws Warning
	 */
	private void readFile(File input) throws Warning {
		try{
			Scanner console = new Scanner(input);
			String name = console.next();
			String name2 = "";
			while (!name.equals("$")){
				myNetwork.addVertex(name);
				name = console.next();
			}
			while (console.hasNextLine()){
				if (console.hasNext()){
					name = console.next();
				}
				else{
					throw new Warning("invalid input file");
				}
				if (console.hasNext()){
					name2 = console.next();
				}
				else{
					throw new Warning("invalid input file");
				}
				myNetwork.addConnection(name, name2);
			}
			console.close();
		}
		catch (FileNotFoundException exception){
			System.out.println("Input file does not exist");
			System.exit(0);
		}

		
	}


}
