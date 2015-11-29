import java.util.Arrays;

/**
 * An Adjacency list form for a social network graph
 * 
 * Note:efficancy can be strongly improved by sorting the lists after entries added then
 * using bianary searchs for Verticies rather than iterators.
 * @author Jeremy
 *
 */
public class Network {
	
	private VertexList vertices;
	
	public Network(){
		vertices = new VertexList();
		
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
	public EdgeList mostPopular(){

		EdgeList mostPopular = new EdgeList();
		return mostPopular;
	}
	/**
	 * returns a list of items in shortest path between start and end
	 * Implemented using BFS with arrays
	 * @param start starting node
	 * @param end ending node
	 * @return list of vertices on shortest path including start and end
	 */
	public EdgeList shortestPath(String start, String end){
		//setup necessary placeholder data structures
		String [] names = vertices.getNames();
		int [] depth = new int[names.length];
		Arrays.fill(depth, -1);
		String [] previous = new String[names.length];
		//populate while performing BFS
		EdgeList currentConnections = vertices.getEdgeList(start);
		int loc = indexOf(names, currentConnections.next());
		depth[loc] = 0;
		previous[loc] = "origin";
		EdgeList bfsQueue = new EdgeList();
		while (currentConnections.hasNext()) {
			String nextName = currentConnections.next();
			loc = indexOf(names, nextName);
			if (depth[loc] == -1) {
				depth[loc] = depth[indexOf(names, previous[loc])] + 1;
				previous[loc] = start;
				bfsQueue.add(nextName);
			}
		}
		while (bfsQueue.hasNext()) {
			String prevName = bfsQueue.next();
			currentConnections = vertices.getEdgeList(prevName);
			currentConnections.next();
			while (currentConnections.hasNext()) {
				String nextName = currentConnections.next();
				loc = indexOf(names, nextName);
				if (depth[loc] == -1) {
					depth[loc] = depth[indexOf(names, previous[loc])] + 1;
					previous[loc] = prevName;
					bfsQueue.add(nextName);
				}
			}
		}
		//Use results of BFS to construct shortest path
		EdgeList path = new EdgeList();
		path.addFront(end);
		loc = indexOf(names, end);
		while (!previous[loc].equals("origin")) {
			path.addFront(previous[loc]);
			loc = indexOf(names, previous[loc]);
		}
		return path;
	}
	
	/**
	 * method to find the index of a given string in an array. Uses a simple linear search.
	 * @param a array to search
	 * @param target the String to search for
	 * @return the index of the target string
	 */
	private int indexOf(String [] a, String target) {
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals(target)) return i;
		} 
		return -1;
	}
	/**
	 * tests for an edge between vert and connect
	 * @param vert the vertex to test from
	 * @param connect the vertex testing a connection to
	 * @return true if the edge exist false if not
	 */
	public Boolean isConnected(String vert, String connect){
		EdgeList myVert = vertices.getEdgeList( vert );
		if (myVert.includes(connect)){
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
	public EdgeList mutualConnections(String vertex1, String vertex2){
		EdgeList connections = new EdgeList();
		return connections;

	}
	
	
	/**
	 * adds a vertex to Network
	 * @param name name of person adding to network
	 */
	public void addVertex(String name){
		vertices.add(name);
	}
	
	/**
	 * adds a connection to the Network by adding vertex connection to both vertices
	 * on the edge
	 * note: this will allow multiple edges between the same vertices if added
	 * @param name1 first vertex on edge
	 * @param name2 second vertex on edge
	 */
	public void addConnection(String name1, String name2){
		EdgeList temp = vertices.getEdgeList(name1);
		temp.add(name2);
		temp = vertices.getEdgeList(name2);
		temp.add(name1);
	}
}
