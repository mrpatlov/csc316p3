/**
 * class defining edgelist objects (queue) for use in the SocialNetwork program
 *Took contributors names out for identity purposes
 */
public class EdgeList {
	
	Node head;
	Node tail;
	
	/**
	 * Primary constructor of EdgeList objects
	 * @param name the string of data to be held as the first item in the list
	 */
	public EdgeList (String name) {
		head = new Node (name);
		tail = head;
	}
	
	/**
	 * blank constructor of EdgeList objects. Sets all fields to null
	 */
	public EdgeList() {
		this.head = null;
		this.tail = null;
	}

	/**
	 * Inner node class used in EdgeLists
	 * 
	 * @author Jeremy Vanderwall
	 * @author John Parsons
	 *
	 */
	class Node {
		String name;
		Node next;

		public Node (String name) {
			this.name = name;
			this.next = null;
		}
	}
	
	/**
	 * determines if an EdgeList object contains a value equal to the target string.
	 * @param target the string to search for
	 * @return true if the target is found
	 */
	public boolean contains(String target) {
		Node temp = head;
		while (temp != null) {
			if (temp.name.equals(target)) {
				return true;
			}
			temp = temp.next;
		}
		return false;
	}

	/**
	 * Adds a new person to the rear of the list.
	 * @param newName the string to add
	 */
	public void add(String newName) {
		Node addThis = new Node(newName);
		if (head == null) {
			head = addThis;
			tail = addThis;
		} else {
			this.tail.next = addThis;
			this.tail = tail.next;
		}
	}
	
	/**
	 * adds a new person to the front of the list
	 * @param newName the string to add
	 */
	public void addFront(String newName) {
		if (head == null) {
			head = new Node(newName);
		} else {
			Node oldHead = head;
			head = new Node(newName);
			head.next = oldHead;
		}
	}

	/**
	 * determines if the list has at least one more value in it
	 * @return true if there is another item in the list
	 */
	public boolean hasNext() {
		if (head == null){
		  	return false;
		}
		return true;
	}

	/**
	 * removes and returns the string at the head of the list
	 * @return the first item in the list
	 */
	public String next() {
		String name = head.name;
		head = head.next;
		return name;
	}
	
	/**
	 * Performs a deep copy of the EdgeList and returns the result
	 * @return the copy
	 */
	public EdgeList copyOf() {
		EdgeList newList = new EdgeList();
		if (head == null) {
			return newList;
		} else {
			Node iter = head;
			newList.add(iter.name);
			while(iter.next != null) {
				iter = iter.next;
				newList.add(iter.name);
			}
			return newList;
		}
	}
}
