/* Modify the MyLinkedList class so that Node includes a new data field 'prev'
 * Implement all the methods already defined in MyLinkedList and in addition
 * methods listIterator() and listIterator(int index). The former sets the cursor to the
 * head of the list while the latter sets the cursor to node at the specified index.
 * Test the revised TwoWayLinkedList
 * 
 * Kaylyn Phan
 * 23 April 2020
 */

package implementingLists;

import java.util.Iterator;
import java.util.ListIterator;


class TwoWayLinkedList<E> implements Iterable<E> {
	private Node<E> head, tail;
	private int size = 0;
	
	public TwoWayLinkedList() {
		//empty
	}
	
	public TwoWayLinkedList(E[] objects) {
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
	
	public MyListIterator listIterator() {
		return new MyListIterator();
	}
	
	public MyListIterator listIterator(int index) {
		MyListIterator iterator = new MyListIterator();
		Node<E> currentNode = head;
		for (int i = 0; i < index; i++) {
			currentNode = currentNode.next;
		}
		iterator.current = currentNode;
		return iterator;
	}
	
	
	private class MyListIterator implements ListIterator<E> {
		Node<E> current = head;

		@Override
		public boolean hasNext() {
			return (current.next != null);
		}

		@Override
		public E next() {
			if (hasNext() == true) {
				current = current.next;
				return current.element;
			}
			return null;
		}

		@Override
		public boolean hasPrevious() {
			return (current.prev != null);
		}

		@Override
		public E previous() {
			if (hasPrevious() == true) {
				current = current.prev;
				return current.element;
			}
			return null;
		}

		@Override
		public int nextIndex() {
			int index = 1;
			Node<E> temp = head;
			while (temp != current) {
				index++;
				temp = temp.next;
			}
			return index;
		}

		@Override
		public int previousIndex() {
			int index = -1;
			Node<E> temp = head;
			while (temp != current) {
				index++;
				temp = temp.next;
			}
			return index;
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

		@Override
		public void set(E e) {
			current.element = e;	
		}

		@Override
		public void add(E e) {
			Node<E> newNode = new Node<E>(e);
			Node<E> right = current.next;
			current.next = newNode;
			right.prev = newNode;
			newNode.prev = current;
			newNode.next = right;
			current = newNode;
			size++;
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
			if (hasNext() == true) {
				current = current.next;
				return current.element;
			}
			return null;
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
				current = current.prev;
				size--;
			}
		
		}
	}

	private static class Node<E> {
		E element;
		Node<E> next;
		Node<E> prev;
	
		public Node(E element) {
			this.element = element;
		}
	}
	
	public int size() {
		return size;
	}
}

public class TestTwoWayLinkedList {
	public static void main(String[] args) {
		String[] names = {"Amy", "Bob", "Charles", "Don", "Emma", "Frank", "George", "Henry", "Isabelle", "John"};
		//construct MyLinkedList with names
		TwoWayLinkedList<String> myList = new TwoWayLinkedList<String>(names);
		System.out.println("Printing the list: " + myList.toString());
		System.out.println();
		
		System.out.println("Testing the ListIterator:");
		System.out.println();
		
		//test listIterator()
		System.out.println("Testing listIterator() which initially points to the head of the list:");
		ListIterator<String> iterator1 = myList.listIterator();
		System.out.println("Has next: " + iterator1.hasNext());
		System.out.println("Has previous: " + iterator1.hasPrevious());
		System.out.println("Next element is: " + iterator1.next());
		System.out.println("And the next element is: " + iterator1.next());
		System.out.println("Previous element is: " + iterator1.previous());
		System.out.println("Previous index is: " + iterator1.previousIndex());
		System.out.println("Next index is: " + iterator1.nextIndex());
		System.out.println("Removing the last returned element, the list is now:");
		iterator1.remove();
		System.out.println(myList.toString());
		System.out.println("Adding 'Chris' at the current position, the list is now:");
		iterator1.add("Chris");
		System.out.println(myList.toString());
		System.out.println("Setting the element at this position to 'Christopher':");
		iterator1.set("Christopher");
		System.out.println(myList.toString());
		System.out.println();
		
		//test listIterator(int index)
		System.out.println("Now testing listIterator(int index), pointing the cursor to the tail of the list:");
		ListIterator<String> iterator2 = myList.listIterator(9);
		System.out.println("Has next: " + iterator2.hasNext());
		System.out.println("Has previous: " + iterator2.hasPrevious());
		System.out.println("Previous element: " + iterator2.previous());
		System.out.println("Removing the last returned element, the list is now:");
		iterator2.remove();
		System.out.println(myList.toString());
		System.out.println();
		
		//testing all other methods
		System.out.println("Testing other methods:");
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
	
	}
}

