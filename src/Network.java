

/**
 * An Adjacency list form for a social network graph
 * 
 * Note:efficancy can be strongly improved by sorting the lists after entries added then
 * using bianary searchs for Verticies rather than iterators.
 * @author Jeremy
 *
 */
public class Network {
	
	private VertexList verticies;
	
	public Network(){
		verticies = new VertexList();
		
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
	 * @param start starting node
	 * @param end ending node
	 * @return list of verticies on shortest path
	 */
	public EdgeList shortestPath(String start, String end){
		EdgeList path = new EdgeList();
		return path;

	}
	/**
	 * tests for an edge between vert and connect
	 * @param vert the vertex to test from
	 * @param connect the vertex testing a connection to
	 * @return true if the edge exist false if not
	 */
	public Boolean isConnected(String vert, String connect){
		EdgeList myVert = verticies.getEdgeList( vert );
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
		verticies.add(name);
	}
	
	/**
	 * adds a connection to the Network by adding vertex connection to both vertices
	 * on the edge
	 * note: this will allow multiple edges between the same vertices if added
	 * @param name1 first vertex on edge
	 * @param name2 second vertex on edge
	 */
	public void addConnection(String name1, String name2){
		EdgeList temp = verticies.getEdgeList(name1);
		temp.add(name2);
		temp = verticies.getEdgeList(name2);
		temp.add(name1);
	}
}
