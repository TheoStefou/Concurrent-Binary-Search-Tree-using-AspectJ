import java.lang.Thread;
import java.util.Random;
import java.util.ArrayList;

class Test extends Thread {

	BinarySearchTree btree;

	public Test(String name, BinarySearchTree btree) {
		super(name);
		this.btree = btree;
	}

	public void run() {

		Random random = new Random();
		for(int i = 0; i < 10; i++) {
			int action = random.nextInt(3);
			if(action <= 1) {
				//Perform a lookup
				//System.out.println("Lookup..."+Thread.currentThread().getName());
				this.btree.lookup(random.nextInt(2000));
			}
			else{
				//Perform an insertion
				//System.out.println("Insert..."+Thread.currentThread().getName());
				this.btree.insert(random.nextInt(2000));
			}
			// else { //action == 2
			// 	//Perform a deletion
			// 	//System.out.println("Remove..."+Thread.currentThread().getName());
			// 	this.btree.remove(random.nextInt(2000));
			// }

		}

	}
}

public class Main {
    
    public static void main(String[] args) {
	
		BinarySearchTree b = new BinarySearchTree();
		b = new BinarySearchTree();
		b = new BinarySearchTree();
		b = new BinarySearchTree();

		ArrayList<Test> L = new ArrayList<Test>();
		//Create 4 threads
		for(int i = 0; i < 4; i++) {
			L.add(new Test("Thread #"+Integer.toString(i), b));

		}
		//Have at it boys
		for(Test t: L)
			t.start();

		//Wait for all of them to stop
		for(Test t: L) {
			try {
				t.join();
			}
			catch(Exception e) {
				System.out.println(e);
			} 
		}

		 //b.printInOrder();
    }
}
