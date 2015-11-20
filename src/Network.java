import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An Adjacency list form for a social network graph
 * 
 * Note:efficancy can be strongly improved by sorting the lists after entries added then
 * using bianary searchs for Verticies rather than iterators.
 * @author Jeremy
 *
 */
public class Network {
	
	private ArrayList<Vertex> verticies;
	
	public Network(){
		verticies = new ArrayList<Vertex>();
		
	}
	
	/**
	 * the number of pairs of vertex that are not connected
	 * @return
	 */
	public int notConnected(){
		
		return 1;
		
	}
	
	/**
	 * list of the most popular people
	 * @return list of the most popular people
	 */
	public ArrayList<String> mostPopular(){
		ArrayList<Vertex> popular = new ArrayList<Vertex>();
		ArrayList<String> mostPopular = new ArrayList<String>();
		Iterator<Vertex> itty = verticies.iterator();
		Vertex temp;
		float popularity = 0;
		float tempPop = 0;
		while (itty.hasNext()){
			temp = itty.next();
			tempPop = temp.popularity();
			if (tempPop > popularity){
				popularity = tempPop;
				popular.clear();
				popular.add(temp);
			}
			else if (tempPop == popularity){
				popular.add(temp);
			}
		}
		
		return mostPopular;
	}
	/**
	 * returns a list of items in shortest path between start and end
	 * @param start starting node
	 * @param end ending node
	 * @return list of verticies on shortest path
	 */
	public ArrayList<String> shortestPath(String start, String end){
		//create variables to hold an iterator the vertices on both ends
		//of the edge and a temp vertex for comparisons
		Iterator<Vertex> itty = verticies.iterator();
		Vertex vert1 = null;
		Vertex vert2 = null;
		Vertex temp;
		//Iterate over the the list to get the vertex on ends of edge
		while (itty.hasNext()){
			temp = itty.next();
			if (temp.name.equals(start)){
				vert1 = temp;
			}
			if (temp.name.equals(end)){
				vert2 = temp;
			}
		}
		//find the shortest path
		ArrayList<String> path = vert1.shortestPath(vert2);
		return path;
	}
	/**
	 * tests for an edge between vert and connect
	 * @param vert the vertex to test from
	 * @param connect the vertex testing a connection to
	 * @return true if the edge exist false if not
	 */
	public Boolean isConnected(String vert, String connect){
		
		//create variables to hold an iterator the vertices on both ends
		//of the edge and a temp vertex for comparisons
		Iterator<Vertex> itty = verticies.iterator();
		Vertex vert1 = null;
		Vertex vert2 = null;
		Vertex temp;
		//Iterate over the the list to get the vertex on ends of edge
		while (itty.hasNext()){
			temp = itty.next();
			if (temp.name.equals(vert)){
				vert1 = temp;
			}
			if (temp.name.equals(connect)){
				vert2 = temp;
			}
		}
		if (vert1.hasConnection(vert2)){
			return true;
		}
		return false;
	}
	
	/**
	 * Makes a list of mutual connections between two vertices
	 * @param vertex1 first vertex
	 * @param vertex2 second vertex
	 * @return list of mutual connections
	 */
	public List<String> mutualConnections(String vertex1, String vertex2){
		//create variables to hold an iterator the vertices on both ends
		//of the edge and a temp vertex for comparisons
		Iterator<Vertex> itty = verticies.iterator();
		Vertex vert1 = null;
		Vertex vert2 = null;
		Vertex temp;
		//Iterate over the the list to get the vertex on ends of edge
		while (itty.hasNext()){
			temp = itty.next();
			if (temp.name.equals(vertex1)){
				vert1 = temp;
			}
			if (temp.name.equals(vertex2)){
				vert2 = temp;
			}
		}
		//Get the list of mutual connections
		ArrayList<String> mutualConnect = vert1.mutual(vert2);
		return mutualConnect;
	}
	
	
	/**
	 * adds a vertex to Network
	 * @param name name of person adding to network
	 */
	public void addVertex(String name){
		verticies.add(new Vertex(name));
	}
	
	/**
	 * adds a connection to the Network by adding vertex connection to both vertices
	 * on the edge
	 * note: this will allow multiple edges between the same vertices if added
	 * @param name1 first vertex on edge
	 * @param name2 second vertex on edge
	 */
	public void addConnection(String name1, String name2){
		
		//create variables to hold an iterator the vertices on both ends
		//of the edge and a temp vertex for comparisons
		Iterator<Vertex> itty = verticies.iterator();
		Vertex vert1 = null;
		Vertex vert2 = null;
		Vertex temp;
		//Iterate over the the list to get the vertex on ends of edge
		while (itty.hasNext()){
			temp = itty.next();
			if (temp.name.equals(name1)){
				vert1 = temp;
			}
			if (temp.name.equals(name2)){
				vert2 = temp;
			}
		}
		//assign connections to vertices
		if (vert1 != null && vert2 !=null){
			vert1.addConnection(vert2);
			vert2.addConnection(vert1);
		}
		
	}
	
	class Vertex{
		
		protected String name;
		protected ArrayList<Vertex> connections;
		
		/**
		 * Constructor
		 * @param name Person being added to Network
		 */
		public Vertex(String name){
			this.name = name;
			connections = new ArrayList<Vertex>();
		}
		
		/**
		 * Do a BFS to detrmine number of connections and distance
		 * @return the popularity of this person
		 */
		public float popularity() {
			ArrayList<Vertex> visited = new ArrayList<Vertex>();
			Iterator<Vertex> itty = connections.iterator();
			int friends = 0;
			int distance = 0;
			while (itty.hasNext()){
				visited.add(itty.next());
				friends++;
				distance++;
			}
			return (float)friends/distance;
		}

		public ArrayList<String> shortestPath(Vertex vert2) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * makes a list of mutual connections between this vertex and vert2
		 * @param vert2 the vertext testing for shared connections
		 * @return list of shared connections.
		 */
		public ArrayList<String> mutual(Vertex vert2) {
			Iterator<Vertex> thisItty = this.connections.iterator();
			Iterator<Vertex> vert2Itty = vert2.connections.iterator();
			ArrayList<String> mutual = new ArrayList<String>();
			Vertex temp;
			while (thisItty.hasNext()){
				temp = thisItty.next();
				while(vert2Itty.hasNext()){
					if (temp.name.equals(vert2Itty.next().name)){
						mutual.add(temp.name);
					}
					
				}
			}
			return mutual;
		}

		/**
		 * tests for a connection to a vertex
		 * @param vert2 vertex testing connection to
		 * @return true if connection exist, false if not
		 */
		public boolean hasConnection(Vertex vert2) {
			if (connections.contains(vert2)){
				return true;
			}
			return false;
		}

		/**
		 * adds connection
		 * @param name vertex adding connection to
		 */
		public void addConnection(Vertex name){
			connections.add(name);
		}
	}

}
