/**
*   @Project: Spell Checker
*   @Author: Ryan Mee
*   @Class: Key
*
*   The Key class contains the object and linked list
*   used for our hash table entries. When searching for
*   a match the head Node will always be the first one
*   checked, following with the rest of the list if needed.
*/

public class Key {
	//first entry in the bucket
	public Node head;
    //used to read through the bucket entries
	public Node current;
	
    /**
        The Node class contains the String of each entry
        and a pointer to the next string in that bucket.
    */
	private class Node {
        //string being stored
		private String value;
        //next string in bucket
		private Node next;
		
        /**
            The constructor method for the Node class.
        */
		public Node(String value, Node next) {
			this.value = value;
			this.next = next;
		}
	}
	
    /**
        Constructor method for the Key class.
        
        Will create a new node with the passed parameters and
        make it the head.
        
        @param String str - string being stored
        @param Node next - the next node in the bucket
    */
	public Key(String str, Node next) {
		this.head = new Node(str, next);
		this.current = head;
	}
	
    /**
        The insert class will add a Node in the head positon
        if the current bucket is empty, or add on to the end
        if not.
        
        @param String str - the string being added
        @param Node next - the next node beings stored
    */
	public void insert(String str, Node next) {
		if (head == null) {
			head = new Node(str, next);
		} else {
			Node current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = new Node(str, next);
		}
	}
	
    /**
        This function will return the current bucket's entries
        in order as a string.
        
        @return String str - the current bucket in order
    */
	public String toString() {
		String str = "";
		Node current = head;
		while (current != null) {
			str += "[" + current.value + "] ";
			current = current.next;
		}
		return str;
	}
	
    /**
        Getter method for next
        
        @return Node next - the next node
    */
	public Node getNext() {
		return current.next;
	}
	
    /**
        Getter method for Value
        
        @return String - current string
    */
	public String getValue() {
		return current.value;
	}
}