package eg.edu.alexu.csd.filestructure.sort;

import eg.edu.alexu.csd.filestructure.sort.INode;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class Heap<T extends Comparable<T>> implements IHeap<T> {
	private int Length = 0;
	private List<INode<T>> elements = new ArrayList<>();

	@SuppressWarnings("hiding")
	private class Node<T extends Comparable<T>> implements INode<T> {
		private int index;
		private T value;

		public Node(int index) {
			this.index = index;
		}

		@SuppressWarnings("unchecked")
		@Override
		public INode<T> getLeftChild() {
			if (2 * index > Length) {
				return null;
			}
			return (INode<T>) elements.get(2 * index);
		}

		@SuppressWarnings("unchecked")
		@Override
		public INode<T> getRightChild() {
			if (2 * index + 1 > Length) {
				return null;
			}
			return (INode<T>) elements.get(2 * index + 1);
		}

		@SuppressWarnings("unchecked")
		@Override
		public INode<T> getParent() {
			if (index / 2 >= Length) {
				return null;
			}
			return (INode<T>) elements.get(index / 2);
		}

		@Override
		public T getValue() {
			return value;
		}

		@Override
		public void setValue(T value) {
			this.value = value;

		}
	}

	public INode<T> getRoot() {
		if (elements.size() <= 1) {
			return null;
		}
		return elements.get(1);
	}

	public int size() {
		return Length;
	}

	void swap(final INode<T> node1, final INode<T> node2) {
		T temp = (T) node1.getValue();
		node1.setValue(node2.getValue());
		node2.setValue(temp);
	}

	public void heapify(INode<T> node) {

		if (node != null) {
			INode<T> left = node.getLeftChild();
			INode<T> right = node.getRightChild();
			INode<T> bigger;
			if (left != null && left.getValue().compareTo(node.getValue()) > 0) {
				bigger = left;
			} else {
				bigger = node;
			}
			if (right != null && right.getValue().compareTo(bigger.getValue()) > 0) {
				bigger = right;
			}
			if (bigger != node) {
				swap(node, bigger);
				if (node != getRoot())
					heapify(node.getParent());
				heapify(bigger);
			}
		}
	}

	public T extract() {
		if (elements.size() <= 1) {
			return null;
		}
		T max = elements.get(1).getValue();
		elements.get(1).setValue(elements.get(Length).getValue());
		elements.remove(Length);
		Length--;
		if (Length != 0) {
			heapify(elements.get(1));
		}
		return max;
	}

	public void insert(T element) {
		if (Length == 0) {
			elements.add(null);
		}
		if (element == null) {
			return;
		}
		INode<T> node = new Node<T>(Length + 1);
		node.setValue(element);
		elements.add(Length + 1, node);
		Length++;
		heapifyUp(node);
	}

	private void heapifyUp(INode<T> node) {
		while (node != getRoot() && node.getParent() != null
				&& node.getValue().compareTo(node.getParent().getValue()) > 0) {
			swap(node, node.getParent());
			node = node.getParent();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void build(Collection unordered) {
		if (unordered == null) {
			Length = 0;
			return;
		}
		
		ArrayList<T> set = new ArrayList<>(unordered);
		elements.add(0, null);

		for (int i = 0; i < unordered.size(); i++) {
			INode<T> k = new Node<T>(i + 1);
			k.setValue(set.get(i));
			elements.add(i + 1, k);
			Length++;
			heapifyUp(elements.get(i + 1));
		}

	}

	IHeap<T> sort(ArrayList<T> unordered) {
		if (unordered.isEmpty()) {
			Length = 0;
			elements.add(null);
			return this;
		}
		this.build(unordered);

		for (int i = Length; i >= 2; i--) {
			swap(elements.get(1), elements.get(i));
			Length--;
			this.heapify(elements.get(1));
		}
		Length = unordered.size();
		return this;
	}

}