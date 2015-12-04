
public class EdgeList {
	
	Node head;
	Node tail;
	
	public EdgeList (String name) {
		head = new Node (name);
		tail = head;
	}
	
	public EdgeList() {
		this.head = null;
		this.tail = null;
	}

	class Node {
		String name;
		Node next;

		public Node (String name) {
			this.name = name;
			this.next = null;
		}
	}

	public boolean includes(String connect) {
		Node temp = head.next;
		while (temp != null) {
			if (temp.name.equals(connect)) {
				return true;
			}
			temp = temp.next;
		}
		return false;
	}

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

	public boolean hasNext() {
		if (head == null){
		  	return false;
		}
		return true;
	}

	public String next() {
		String name = head.name;
		head = head.next;
		return name;
	}
	
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
