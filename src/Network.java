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
	 * the number of pairs of vertices that are not connected
	 * @return
	 */
	public int notConnected(){
		String [] names = vertices.getNames();
		int [] components = new int[names.length];
		int val = 0;
		Arrays.fill(components, -1);
		EdgeList tempEdg = new EdgeList();
		VertexList tempVer = new VertexList();
		EdgeList currentConnections = vertices.getEdgeList(names[0]);
		tempVer.add(names[0]);
		tempEdg.add(names[0]);
		while(currentConnections.hasNext()){
			String nextName = currentConnections.next();
			if(!(tempEdg.includes(nextName))){
				tempEdg.add(nextName);
				val++;
			}
		}
		while(tempEdg.hasNext()){
			String tempName = tempEdg.next();
			currentConnections = vertices.getEdgeList(tempName);
			while(currentConnections.hasNext()){
				String nextName = currentConnections.next();
				if(!(tempEdg.includes(nextName))){
					tempEdg.add(nextName);
					val++;
				}
			}
		}
		while(tempEdg.hasNext()){
			tempVer.add(tempEdg.next());
		}
		int iter = 0;
		while(components[iter] != -1){
			iter++;
		}
		components[iter] = val;
		val = 0;
		iter = 0;
		while(iter < names.length){
			if(!(tempVer.includes(names[iter]))){
				currentConnections = vertices.getEdgeList(names[iter]);
				tempVer.add(names[iter]);
				tempEdg = new EdgeList();
				tempEdg.add(names[iter]);
				while(currentConnections.hasNext()){
					String nextName = currentConnections.next();
					if(!(tempEdg.includes(nextName))){
						tempEdg.add(nextName);
						val++;
					}
				}
				while(tempEdg.hasNext()){
					String tempName = tempEdg.next();
					currentConnections = vertices.getEdgeList(tempName);
					while(currentConnections.hasNext()){
						String nextName = currentConnections.next();
						if(!(tempEdg.includes(nextName))){
							tempEdg.add(nextName);
							val++;
						}
					}
				}
				while(tempEdg.hasNext()){
					tempVer.add(tempEdg.next());
				}
				iter = 0;
				while(components[iter] == -1){
					iter++;
				}
				components[iter] = val;
				iter = 0;
			}
		}
		
		iter = 0;
		while(components[iter] != -1){
			iter++;
		}
		
		if(iter == 0 || iter == 1){
			return 0;
		}
		
		int answer = 0;
		for(int i = 0; i < (iter - 1); i++){
			for(int j = (i + 1); j < iter; j++){
				answer += (components[i]*components[j]);
			}
		}
		
		return answer;
		
	}
	
	/**
	 * list of the most popular people
	 * Loops through each vertex, grabbing the edge list of each one. 
	 * Then counts how many edges that vertex has, and compares it to the max.
	 * @return list of the most popular people
	 */
	public EdgeList mostPopular(){
		EdgeList mostPopular = new EdgeList();
		int length = vertices.size();
		String names[] = vertices.getNames();
		int max = 0;
		int current = 0;
		for(int i = 0; i < length; i++) {
			EdgeList e = vertices.getEdgeList(names[i]);
			while(e.hasNext()) {
				current++;
				e.next();
			}
			if(current > max) {
				mostPopular = new EdgeList();
				mostPopular.add(names[i]);
			} else if(current == max) {
				mostPopular.add(names[i]);
			} else if(current < max) {
				
			}
		}
				
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
