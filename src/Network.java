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
	 * this calculates the number of pairs of verticies that are
	 * not connected using an inefficient nieve method
	 * @return the number of un connected pairs of verticies
	 */
	public int notConnected2(){
		//all the names in the vertex list
		String [] names = vertices.getNames();
		//running tally of non-connections
		int notConnected = 0;
		for (int i = 0; i < names.length; i++){ //for each name in the list
			for (int j = 0; j < names.length; j++){ //test against every other name in the list
				if (shortestPath(names[i], names[j]) == null){  //if no path exists
					notConnected++;
				}
			}
		}
		//if "a" and "b" are not connected both a->b and b->a
		//don't exist and have been counted, meaning all non connections are
		//double counted.  divide by two to fix
		return notConnected/2;
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
		EdgeList currentConnections = vertices.readEdgeList(names[0]);
		tempVer.add(names[0]);
		tempEdg.add(names[0]);
		while(currentConnections.hasNext()){
			String nextName = currentConnections.next();
			if(!(tempEdg.contains(nextName))){
				tempEdg.add(nextName);
				val++;
			}
		}
		while(tempEdg.hasNext()){
			String tempName = tempEdg.next();
			currentConnections = vertices.readEdgeList(tempName);
			while(currentConnections.hasNext()){
				String nextName = currentConnections.next();
				if(!(tempEdg.contains(nextName))){
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
			if(!(tempVer.contains(names[iter]))){
				currentConnections = vertices.readEdgeList(names[iter]);
				tempVer.add(names[iter]);
				tempEdg = new EdgeList();
				tempEdg.add(names[iter]);
				while(currentConnections.hasNext()){
					String nextName = currentConnections.next();
					if(!(tempEdg.contains(nextName))){
						tempEdg.add(nextName);
						val++;
					}
				}
				while(tempEdg.hasNext()){
					String tempName = tempEdg.next();
					currentConnections = vertices.readEdgeList(tempName);
					while(currentConnections.hasNext()){
						String nextName = currentConnections.next();
						if(!(tempEdg.contains(nextName))){
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
		//this is the list of the most popular people
		EdgeList mostPopular = new EdgeList();
		
		//length of vertex list
		int length = vertices.size();
		
		//an array holding all the names in the vetex list
		String names[] = vertices.getNames();
		//number of friends
		int friends[] = new int[names.length];
		//total distance to friends
		int distance[] = new int[names.length];
				
		//calculate the popularity for each vertex
		for(int i = 0; i < length; i++) {
			//make a copy of the edge list
			EdgeList e = this.vertices.getEdgeList(names[i]).copyOf();
			//make a list of all this verticies friends
			EdgeList myFriends = new EdgeList();
			int depth = 1;
			//first element in edge list is vertex, so discard
			e.next();
			
			//add all direct connections to myFriends list
			while (e.hasNext()){
				//add friend to list
				myFriends.add(e.next());
				//increment friend count
				friends[i]++;
				//add appropriate distance
				distance[i] += depth;
			}
			
			//now friends of friends need to added
			boolean finished = false;
			while (!finished){
				//make a destructable copy of friends list
				EdgeList friendsTemp = myFriends.copyOf();
				finished = true;
				//each itteration of this loop is one step futher removed, so
				//depth needs to be incremented
				depth++;
				//itterate over all friends to add more
				while( friendsTemp.hasNext()){
					EdgeList friendsFriends = this.vertices.getEdgeList(friendsTemp.next()).copyOf();
					while (friendsFriends.hasNext()){
						String name = friendsFriends.next();
						if (myFriends.contains(name)){
							//do nothing if name is already in list
						}
						else{
							//add the friend
							myFriends.add(name);
							//increment friend count
							friends[i]++;
							//add appropriate distance
							distance[i] += depth;
							//added a friend so more connections my be found
							finished = false;
						}
					}
					
				}
				
			}
		}
		
		//a properly populated keyed list for verticies with paired lists for friends and distance
		//now exist  It is time to calculate most popular and populate the list
		float max = 0;
		for (int i = 0; i < length; i++){
			if ((float)friends[i]/distance[i] > max){
				mostPopular = new EdgeList();
				mostPopular.add(names[i]);
				max = (float)friends[i]/distance[i];
			}else if ((float)friends[i]/distance[i] == max){
				mostPopular.add(names[i]);
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
		EdgeList currentConnections = vertices.readEdgeList(start);
		int loc = indexOf(names, currentConnections.next());
		depth[loc] = 0;
		previous[loc] = "origin";
		EdgeList bfsQueue = new EdgeList();
		while (currentConnections.hasNext()) {
			String nextName = currentConnections.next();
			loc = indexOf(names, nextName);
			if (depth[loc] == -1) {
				previous[loc] = start;
				depth[loc] = depth[indexOf(names, previous[loc])] + 1;
				bfsQueue.add(nextName);
			}
		}
		while (bfsQueue.hasNext()) {
			String prevName = bfsQueue.next();
			currentConnections = vertices.readEdgeList(prevName);
			while (currentConnections.hasNext()) {
				String nextName = currentConnections.next();
				loc = indexOf(names, nextName);
				if (depth[loc] == -1) {
					previous[loc] = prevName;
					depth[loc] = depth[indexOf(names, previous[loc])] + 1;
					bfsQueue.add(nextName);
				}
			}
		}
		//Use results of BFS to construct shortest path
		EdgeList path = new EdgeList();
		path.addFront(end);
		loc = indexOf(names, end);
		if (previous[loc] == null) return null;
		while (!previous[loc].equals("origin")) {
			path.addFront(previous[loc]);
			loc = indexOf(names, previous[loc]);
			if (previous[loc] == null) return null;
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
		EdgeList myVert = vertices.readEdgeList( vert );
		myVert.next();
		if (myVert.contains(connect)){
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
		EdgeList first = vertices.readEdgeList(vertex1);
		first.next();
		EdgeList second = vertices.readEdgeList(vertex2);
		second.next();
		while (first.hasNext()) {
			String current = first.next();
			if (second.contains(current)) {
				connections.add(current);
			}
		}
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
