
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
			if (temp.name.head.equals(vert)){
				return temp.name;
			}
		}
		return null;
	}


}
