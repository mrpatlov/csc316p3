import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SocialNetwork {
	
	Network myNetwork;
	
	public SocialNetwork(){
		myNetwork = new Network();
	}

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
			throw new Warning("No Command found");
		}
		
		//process command
		if (command.equals("notconnected")){
			int notconnected = myNetwork.notConnected();
			System.out.println(notconnected);
		}
		else if (command.equals("isfriend")){
			if (input.hasNext()){
				person = input.next();
			}
			else{
				input.close();
				throw new Warning("noconnected needs two arguments");
			}
			if (input.hasNext()){
				friend = input.next();
			}
			else{
				input.close();
				throw new Warning("noconnected needs two arguments");
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
			if (path == null) {
				System.out.println("$");
			} else {
				while (path.hasNext()){
					System.out.println(path.next());
				}
			}
		}
		else{
			input.close();
			throw new Warning("Invald command");
		}
		System.out.println("$");
		input.close();
	}

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
