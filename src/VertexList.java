
/**
 * class defining VertexList objects (graph) for use in the SocialNetwork program
 *
 */
public class VertexList {
	
	Node head;
	Node tail;
	
	/**
	 * inner node class used in VertexLists. Contains an EdgeList
	 * @author Jeremy Vanderwall
	 *
	 */
	class Node {
		EdgeList name;
		Node next;
		public Node (String name){
			this.name = new EdgeList (name);
			if (head == null){
				head = this;
				tail = this;
			}
			else {
				tail.next = this;
				tail = this;
			}
		}
	}

	/**
	 * adds a new EdgeList to the rear of the VertexList
	 * @param name the name of the person to add to the front of the new EdgeList
	 */
	public void add(String name) {
		new Node (name);
		
	}

	/**
	 * returns a shallow copy of the EdgeList that begins with the given name
	 * @param vert the name of the person to look for
	 * @return the EdgeList that contains adjacency info for the given person
	 */
	public EdgeList getEdgeList(String vert) {
		Node temp = head;
		while (temp != null){
			if (temp.name.head.name.equals(vert)){
				return temp.name;
			} else {
				temp = temp.next;
			}
		}
		return null;
	}
	
	/**
	 * returns a deep copy of the EdgeList that begins with the given name
	 * @param vert the name of the person to look for
	 * @return the EdgeList that contains adjacency info for the given person
	 */
	public EdgeList readEdgeList(String vert) {
		Node temp = head;
		while (temp != null){
			if (temp.name.head.name.equals(vert)){
				return temp.name.copyOf();
			} else {
				temp = temp.next;
			}
		}
		return null;
	}
	
	/**
	 * returns an array containing all the names in the system
	 * @return the array of names
	 */
	public String [] getNames() {
		String [] names = new String[size()];
		Node iter = head;
		for (int i = 0; i < size(); i++) {
			names[i] = iter.name.head.name;
			iter = iter.next;
		}
		return names;
	}
	
	/**
	 * size of vertex list
	 * @return number of vertices in list
	 */
	public int size() {
		int size;
		if (head != null) {
			Node iter = head;
			size = 1;
			while (iter.next != null) {
				size++;
				iter = iter.next;
			}
			return size;
		} else {
			return 0;
		}
	}
	
	/**
	 * Searches the VertexList for the given name
	 * @param connect the name to search for
	 * @return true if the name is found
	 */
	public boolean contains(String connect) {
		Node temp = head;
		while (temp != null){
			if (temp.name.equals(connect)){
				return true;
			}
			temp = temp.next;
		}
		return false;
	}


}
