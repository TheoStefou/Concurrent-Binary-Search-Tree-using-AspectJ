//This BST is thread safe, as long as it is compiled and run with LockAspect.aj
public class BinarySearchTree <E extends Comparable<E>>{

	BinarySearchTreeNode<E> root;

	public BinarySearchTree() {
		root = null;
	}

	public boolean insert(E i) {

		if(root == null) {
			root = new BinarySearchTreeNode<E>(i);
			return true;
		}

		BinarySearchTreeNode<E> tmp = root;

		int cmp;
		do {

			cmp = i.compareTo(tmp.data);//i - tmp.data;
			if(cmp < 0) {
				if(tmp.left != null) {
					tmp = tmp.left;
				}
				else break;
			}
			else if(cmp > 0) {
				if(tmp.right != null) {
					tmp = tmp.right;
				}
				else break;
			}
			else{
				//If cmp = 0, the item is already in the tree. We may stop here 
				return false;
			} 
		} while(true);

		if(cmp < 0) {
			tmp.left = new BinarySearchTreeNode<E>(i);
		}
		else{ //Will always be cmp > 0. If cmp was ever zero we would have returned in the loop above.
			tmp.right = new BinarySearchTreeNode<E>(i);
		}

		return true;

	}

	public boolean lookup(E i) {

		BinarySearchTreeNode<E> tmp = root;

		while(tmp != null) {
			int cmp = cmp = i.compareTo(tmp.data);//i - tmp.data;

			if(cmp < 0) {
				tmp = tmp.left;
			}
			else if(cmp > 0) {
				tmp = tmp.right;
			}
			else return true;
		}
		return false;
		
	}

	public E remove(E i) {

		if(root == null)
			return null;

		BinarySearchTreeNode<E> tmp = root;
		BinarySearchTreeNode<E> parent = null;
		int cameFrom = -1; //Parent has child in left = 0 or right = 1
		
		while(tmp.data != i) {
			int cmp = cmp = i.compareTo(tmp.data);//i - tmp.data;
			if(cmp < 0) {
				
				if(tmp.left == null)
					return null;
				else {
					parent = tmp;
					tmp = tmp.left;
					cameFrom = 0; //Came to child from left
				}
			}
			else if(cmp > 0) {
				
				if(tmp.right == null)
					return null;
				else {
					parent = tmp;
					tmp = tmp.right;
					cameFrom = 1; //Came to child from right
				}
			}
			else break;
		}

		//At this point, tmp holds the node in which the value
		//we are searching for was found and parent is the node
		//that led us to tmp

		//Calculate number of chilren
		int num_children = 0;
		if(tmp.left != null) {
			num_children++;
		}
		if(tmp.right != null) {
			num_children++;
		}


		//tmp = node to be deleted: is leaf
		if(num_children == 0) {
			//Unlink node from parent
			if(parent != null) {
				if(cameFrom == 0) {
					parent.left = null;
					return tmp.data;
				}
				else {
					parent.right = null;
					return tmp.data;
				}
			}
			else { //root is leafnode
				root = null;
				return tmp.data;
			}
		}

		//tmp = node to be deleted: has exactly one child
		if(num_children == 1) {

			E valueRemoved;

			if(tmp.left != null) {
				BinarySearchTreeNode<E> child = tmp.left;
				valueRemoved = tmp.data;
				tmp.data = child.data;
				tmp.left = child.left;
				tmp.right = child.right;
			}
			else { //tmp.right != null
				BinarySearchTreeNode<E> child = tmp.right;
				valueRemoved = tmp.data;
				tmp.data = child.data;
				tmp.left = child.left;
				tmp.right = child.right;
			}
			return valueRemoved;
		}

		//tmp = node to be deleted: has two children
		if(num_children == 2) {
			//Go right once, then left until you find a leaf node
			//Replace tmp with that leaf node.
			E valueRemoved = tmp.data;
			BinarySearchTreeNode<E> tmpParent = null;
			BinarySearchTreeNode<E> tmpTwoChildren = tmp.right;

			while(tmpTwoChildren.left != null) {
				tmpParent = tmpTwoChildren;
				tmpTwoChildren = tmpTwoChildren.left;
			}

			//Update value of tmp
			tmp.data = tmpTwoChildren.data;

			if(tmpParent != null) {
				//Need to update parent
				tmpParent.left = null; 

			}
			else {
				//Went right once, but didn't keep going left
				//Need to connect tmp with the rigth side of the first right node
				tmp.right = tmpTwoChildren.right;
			}

			return valueRemoved;
		}

		//Never executes but compiler will complain otherwise
		return null;
	}
}

class BinarySearchTreeNode<E> {
	public E data;
	public BinarySearchTreeNode<E> left;
	public BinarySearchTreeNode<E> right;
	public BinarySearchTreeNode(E data) {
		this.data = data;
	}
}