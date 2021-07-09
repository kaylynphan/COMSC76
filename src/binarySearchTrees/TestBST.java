/* Implement a BST class and write a driver program to test the BST class for all its requiremenets.
 * 
 * Kaylyn Phan
 * 30 April 2020
 */

package binarySearchTrees;

import java.util.Iterator;
import java.util.ArrayList;

class BST<E extends Comparable<E>> {
	protected TreeNode<E> root;
	protected int size = 0;
	
	public BST() {
	}

	public BST(E[] objects) {
		for (int i = 0; i < objects.length; i++) {
			insert(objects[i]);
		}
	}
	
	public boolean search (E e) {
		TreeNode<E> current = root;
		
		while (current != null) {
			if (e.compareTo(current.element) < 0) {
				current = current.left;
			} else if (e.compareTo(current.element) > 0) {
				current = current.right;
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean insert(E e) {
		if (root == null) {
			root = createNewNode(e);
		} else {
			TreeNode<E> parent = null;
			TreeNode<E> current = root;
			while (current != null) {
				if (e.compareTo(current.element) < 0) {
					parent = current;
					current = current.left;
				} else if (e.compareTo(current.element) > 0) {
					parent = current;
					current = current.right;
				} else {
					return false;
				}
			}
			if (e.compareTo(parent.element) < 0) {
				parent.left = createNewNode(e);
			} else {
				parent.right = createNewNode(e);
			}
		}
		size++;
		return true;
	}
	
	protected TreeNode<E> createNewNode(E e) {
		return new TreeNode<>(e);
	}
	
	public void inorder() {
		inorder(root);
	}
	
	protected void inorder(TreeNode<E> root) {
		if (root == null) {
			return;
		}
		inorder(root.left);
		System.out.print(root.element + " ");
		inorder(root.right);
	}
	
	public void postorder() {
		postorder(root);
	}
	
	protected void postorder(TreeNode<E> root) {
		if (root == null) {
			return;
		}
		postorder(root.left);
		postorder(root.right);
		System.out.print(root.element + " ");
	}
	
	public void preorder() {
		preorder(root);
	}
	
	protected void preorder(TreeNode<E> root) {
		if (root == null) {
			return;
		}
		System.out.print(root.element + " ");
		preorder(root.left);
		preorder(root.right);
	}
	
	public static class TreeNode<E> {
		protected E element;
		protected TreeNode<E> left;
		protected TreeNode<E> right;
		
		public TreeNode(E e) {
			element = e;
		}
	}

	public int getSize() {
		return size;
	}
	
	public TreeNode<E> getRoot() {
		return root;
	}
	
	public java.util.ArrayList<TreeNode<E>> path(E e) {
		java.util.ArrayList<TreeNode<E>> list = new java.util.ArrayList<>();
		TreeNode<E> current = root;
		while (current != null) {
			list.add(current);
			if (e.compareTo(current.element) < 0) {
				current = current.left;
			} else if (e.compareTo(current.element) > 0) {
				current = current.right;
			} else {
				break;
			}
		}
		return list;
	}
	
	public boolean delete(E e) {
		TreeNode<E> parent = null;
		TreeNode<E> current = root;
		while (current != null) {
			if (e.compareTo(current.element) < 0) {
				parent = current;
				current = current.left;
			} else if (e.compareTo(current.element) > 0) {
				parent = current;
				current = current.right;
			} else {
				break;
			}
		}
		if (current == null) {
			return false;
		}
		if (current.left == null) {
			if (parent == null) {
				root = current.right;
			} else {
				if (e.compareTo(parent.element) < 0) {
					parent.left = current.right;
				} else {
					parent.right = current.right;
				}
			}
		} else {
			TreeNode<E> parentOfRightMost = current;
			TreeNode<E> rightMost = current.left;
			while (rightMost.right != null) {
				parentOfRightMost = rightMost;
				rightMost = rightMost.right;
			}
			current.element = rightMost.element;
			if (parentOfRightMost.right == rightMost) {
				parentOfRightMost.right = rightMost.left;
			} else {
				parentOfRightMost.left = rightMost.left;
			}
		}
		size--;
		return true;	
	}
	
	public Iterator<E> iterator() {
		return new InorderIterator();
	}
	
	private class InorderIterator implements Iterator<E> {
		private ArrayList<E> list = new ArrayList<>();
		private int current = 0;
		
		public InorderIterator() {
			inorder();
		}
		
		private void inorder() {
			inorder(root);
		}
		
		private void inorder(TreeNode<E> root) {
			if (root == null) {
				return;
			}
			inorder(root.left);
			list.add(root.element);
			inorder(root.right);
		}
		
		public boolean hasNext() {
			if (current < list.size()) {
				return true;
			}
			return false;
		}
		
		public E next() {
			return list.get(current++);
		}
		
		public void remove() {
			if (current == 0) {
				throw new IllegalStateException();
			}
			delete(list.get(--current));
			list.clear();
			inorder();
		}	
	}
	
	public void clear() {
		root = null;
		size = 0;
	}
}

public class TestBST {
	public static void main(String[] args) {
		BST<String> tree = new BST<>();
		//test insert
		tree.insert("Claire");
		tree.insert("Fred");
		tree.insert("April");
		tree.insert("Grace");
		tree.insert("Diane");
		tree.insert("Elizabeth");
		tree.insert("Britney");
		
		// test inorder, postorder, and preorder
		System.out.print("Inorder: ");
		tree.inorder();
		System.out.println();
		System.out.print("Postorder: ");
		tree.postorder();
		System.out.println();
		System.out.print("Preorder: ");
		tree.preorder();
		System.out.println();
		
		//test size
		System.out.println("Number of nodes: " + tree.getSize());
		//test search
		System.out.println("Priscilla is in the tree: " + tree.search("Priscilla"));
		
		//test path
		System.out.println("Path from root to Diane:");
		ArrayList<BST.TreeNode<String>> path = tree.path("Diane");
		for (int i = 0; path != null && i < path.size(); i++) {
			System.out.println(path.get(i).element + " ");
		}
		
		Integer[] numbers = {1, 6, 3, 7, 2, 4, 8, 9};
		BST<Integer> intTree = new BST<>(numbers);
		
		System.out.print("Inorder: ");
		intTree.inorder();
	}

}
