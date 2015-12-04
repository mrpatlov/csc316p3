

public class VertexList {
	
	Node head;
	Node tail;
	
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

	public void add(String name) {
		new Node (name);
		
	}

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
	
	public String [] getNames() {
		String [] names = new String[size()];
		Node iter = head;
		for (int i = 0; i < size(); i++) {
			names[i] = iter.name.head.name;
			iter = iter.next;
		}
		return names;
	}
	
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
