import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SocialNetwork {
	
	Network myNetwork;
	
	public SocialNetwork(){
		myNetwork = new Network();
	}

	public static void main(String[] args) {
		SocialNetwork myNetwork = new SocialNetwork();
		
		File input = new File(args[1]);
		myNetwork.readFile(input);
		
		Scanner console = new Scanner(System.in);
		while (console.hasNextLine()){
			myNetwork.processCommand(console.nextLine());
		}
		console.close();

	}

	private void processCommand(String nextLine) {
		// TODO Auto-generated method stub
		
	}

	private void readFile(File input) {
		Scanner console;
		try{
			console = new Scanner(input);
			String name = console.next();
			while (!name.equals("$")){
				myNetwork.addVertex(name);
				name = console.next();
			}
			while (console.hasNext()){
				myNetwork.addConnection(console.next(), console.next());
			}
		}
		catch (FileNotFoundException exception){
			
		}
		
	}


}
