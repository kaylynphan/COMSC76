/* Complete all the methods in MyLinkedList class and write a test class that initializes
 * a linked list of 10 names and test each method to ensure the class meets all its requirements
 * 
 * Kaylyn Phan
 * 18 April 2020
 */

package implementingLists;

import java.util.Iterator;

class MyLinkedList<E> implements Iterable<E>{
	private Node<E> head, tail;
	private int size = 0;
	
	public MyLinkedList() {
		//empty
	}
	
	public MyLinkedList(E[] objects) {
		for (int i = 0; i < objects.length; i++) {
			addLast(objects[i]);
		}
	}
	
	public E getFirst() {
		if (size == 0) {
			return null;
		} else {
			return head.element;
		}
	}
	
	public E getLast() {
		if (size == 0) {
			return null;
		} else {
			return tail.element;
		}
	}
	
	public void addFirst(E e) {
		Node<E> newNode = new Node<>(e);
		newNode.prev = null;
		newNode.next = head;
		head = newNode;
		size++;
		if (tail == null) {
			tail = head;
		}
	}
	
	public void addLast(E e) {
		Node<E> newNode = new Node<>(e);
		if (tail == null) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			newNode.next = null;
			tail = tail.next;
		}
		size++;
	}
	
	public void add(int index, E e) {
		if (index == 0 ) {
			addFirst(e);
		} else { 
			Node<E> current = head;
			for (int i = 1; i < index; i++) {
				current = current.next;
			}
			Node<E> temp = current.next;
			current.next = new Node<>(e);
			(current.next).prev = current;
			(current.next).next = temp;
			size++;
		}
	}
	
	public E removeFirst() {
		if (size == 0) {
			return null;
		} else {
			Node<E> temp = head;
			head = head.next;
			size--;
			if (head == null) {
				tail = null;
			} else {
				head.prev = null;
			}
			return temp.element;
		}
	}
	
	public E removeLast() {
		if (size == 0 ) {
			return null;
		} else if (size == 1) {
			Node<E> temp = head;
			head = tail = null;
			size = 0;
			return temp.element;
		} else {
			Node<E> current = head;
			for (int i = 0; i < size - 2; i++) {
				current = current.next;
			}
			Node<E> temp = tail;
			tail = tail.prev;
			tail.next = null;
			size--;
			return temp.element;
		}
	}
	
	public E remove(int index) {
		if (index < 0 || index >= size) {
			return null;
		} else if (index == 0) {
			return removeFirst();
		} else if (index == size - 1) {
			return removeLast();
		} else {
			Node<E> previous = head;
			for (int i = 1; i < index; i++) {
				previous = previous.next;
			}
			Node<E> current = previous.next;
			previous.next = current.next;
			previous.next.prev = previous;
			size--;
			return current.element;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			result.append(current.element);
			current = current.next;
			if (current != null) {
				result.append(", ");
			} 
		}
		result.append("]");
		return result.toString();
	}
	
	public void clear() {
		size = 0;
		head = tail = null;
	}
	
	public boolean contains(Object e) {
		if (size == 0) {
			return false;
		} else {
			Node<E> current = head;
			while (current != null) {
				if (current.element == e) {
					return true;
				}
				current = current.next;
			}
			return false;
		}
	}
	
	public E get(int index) {
		if (index < 0 || index >= size) {
			return null;
		} else if (index == 0) {
			return getFirst();
		} else if (index == size - 1) {
			return getLast();
		} else {
			Node<E> current = head.next;
			for (int i = 1; i < index; i++) {
				current = current.next;
			}
			return current.element;
		}
	}
	
	public int indexOf(Object e) {
		if (contains(e) == false) {
			return -1;
		} else if (head.element == e) {
			return 0;
		} else {
			Node<E> current = head.next;
			int index = 1;
			while (current.element != e) {
				current = current.next;
				index++;
			}
			return index;
		}
	}
	
	public int lastIndexOf(E e) {
		if (contains(e) == false) {
			return -1;
		} else {
			Node<E> current = head;
			int index = 0;
			for (int i = 0; i < size; i++) {
				if (current.element == e) {
					index = i;
				}
				current = current.next;
			}
			return index;
		}
	}
	
	public E set(int index, E e) {
		if (index < 0 || index >= size) {
			return null;
		} else {
			Node<E> current = head;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
			current.element = e;
			return e;
		}
	}
	
	public Iterator<E> iterator() {
		return new LinkedListIterator();
	}	

	private class LinkedListIterator implements Iterator<E> {
		private Node<E> current = head;
	
		@Override
		public boolean hasNext() {
			return (current.next != null);
		}
	
		@Override
		public E next() {
			current = current.next;
			return current.element;
		}
	
		@Override
		public void remove() {
			if (current != null) {
				if (current.next != null) {
					current.next.prev = current.prev;
				}
				if (current.prev != null) {
					current.prev.next = current.next;
				}
				current = current.next;
				size--;
			}
		
		}
	}

	private static class Node<E> {
		E element;
		Node<E>  next;
		Node<E> prev;
	
		public Node(E element) {
			this.element = element;
		}
	}
	
	public int size() {
		return size;
	}
}

public class TestMyLinkedList {
	public static void main(String[] args) {
		String[] names = {"Amy", "Bob", "Charles", "Don", "Emma", "Frank", "George", "Henry", "Isabelle", "Charles", "John"};
		//construct MyLinkedList with names
		MyLinkedList<String> myList = new MyLinkedList<String>(names);
		
		//Print Original List
		System.out.println("Original list: " + myList.toString());
		
		//test getFirst
		System.out.println("First Element: " + myList.getFirst());
		
		//test getLast
		System.out.println("Last Element: " + myList.getLast());
		System.out.println();
		
		//test addFirst
		System.out.println("Add \"Alice\" to the front: ");
		myList.addFirst("Alice");
		System.out.println(myList.toString());
		System.out.println();
		
		//test addLast
		System.out.println("Add \"Kim\" to the end: ");
		myList.addLast("Kim");
		System.out.println(myList.toString());
		System.out.println();
		
		//test add(int index, E e)
		System.out.println("Insert \"Drake\" at index 5: ");
		myList.add(5, "Drake");
		System.out.println(myList.toString());
		System.out.println();
		
		//test removeFirst
		System.out.println("Remove first element: ");
		myList.removeFirst();
		System.out.println(myList.toString());
		System.out.println();
		
		//test removeLast
		System.out.println("Remove last element: ");
		myList.removeLast();
		System.out.println(myList.toString());
		System.out.println();

		//test remove(int index)
		System.out.println("Remove element at index 4: ");
		myList.remove(4);
		System.out.println(myList.toString());
		System.out.println();
		//remove an index that is out of bounds
		System.out.println("Remove element at index 20: ");
		System.out.println("This returns: " + myList.remove(20));
		System.out.println();
		
		//test contains
		//"Emma" is an item in the list
		System.out.println("The list contains \"Emma\": " + myList.contains("Emma"));
		//"Peter" is not an item in the list
		System.out.println("The list contains \"Peter\": " + myList.contains("Peter"));
		System.out.println();
		
		//test get
		System.out.println("Getting element at index 7: " + myList.get(7));
		//test an index out of bounds
		System.out.println("Getting element at index 20: " + myList.get(20));
		System.out.println();
		
		//test indexOf
		//"Charles" is in the list twice—this should print lower index
		System.out.println("The index of \"Charles\" is: " + myList.indexOf("Charles"));
		//"Harry" is not in the list
		System.out.println("The index of \"Harry\" is: " + myList.indexOf("Harry"));
		System.out.println();
		
		//test lastIndexOf
		//"Charles" is in the list twice—this should print higher index
		System.out.println("The last index of \"Charles\" is: " + myList.lastIndexOf("Charles"));
		//"Harry" is not in the list
		System.out.println("The last index of \"Harry\" is: " + myList.lastIndexOf("Harry"));
		System.out.println();
		
		//test set
		System.out.println("Set the element at index 6 to be \"Georgina\":");
		myList.set(6, "Georgina");
		System.out.println(myList.toString());
		System.out.println();
		//test set for an index that is out of bounds
		System.out.print("Set the element at index 20 to be \"Zoe\": ");
		System.out.println("This returns: " + myList.set(20, "Zoe"));
		System.out.println();
		
		//test size
		System.out.println("List size is: " + myList.size());
		System.out.println();
		
		//test iterator methods
		System.out.println("Testing iterator methods:");
		Iterator<String> iterator = myList.iterator();
		//test hasNext at the beginning
		System.out.println("Has next: " + iterator.hasNext());
		//test next
		System.out.println("The next element is: " + iterator.next());
		//test remove
		iterator.remove();
		System.out.println("Remove the element above: Now the list is: " + myList.toString());
		System.out.println("Now the size is: " + myList.size());
		System.out.println();
		
		//test clear
		System.out.print("Clearing the list: ");
		myList.clear();
		System.out.println("Now, the list is: " + myList.toString());
	}
}

	

