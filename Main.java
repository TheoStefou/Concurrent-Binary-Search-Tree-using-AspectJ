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
			int action = 0;//random.nextInt(3);
			if(action == 0) {
				//Perform a lookup
				//System.out.println("Lookup..."+Thread.currentThread().getName());
				this.btree.lookup(random.nextInt(2000));
			}
			else if(action == 1){
				//Perform an insertion
				//System.out.println("Insert..."+Thread.currentThread().getName());
				this.btree.insert(random.nextInt(2000));
			}
			else { //action == 2
				//Perform a deletion
				//System.out.println("Remove..."+Thread.currentThread().getName());
				this.btree.remove(random.nextInt(2000));
			}

		}

	}
}

public class Main {
    
    public static void main(String[] args) {
	
		BinarySearchTree b1 = new BinarySearchTree();

		BinarySearchTree b2 = new BinarySearchTree();

		ArrayList<Test> L1 = new ArrayList<Test>();
		//Create 4 threads
		for(int i = 0; i < 4; i++) {
			L1.add(new Test("Thread #"+Integer.toString(i), b1));
		}

		ArrayList<Test> L2 = new ArrayList<Test>();
		//Create 4 threads
		for(int i = 4; i < 8; i++) {
			L2.add(new Test("Thread #"+Integer.toString(i), b2));
		}
		//Have at it boys
		for(Test t: L1)
			t.start();

		for(Test t: L2)
			t.start();

		//Wait for all of them to stop
		for(Test t: L1) {
			try {
				t.join();
			}
			catch(Exception e) {
				System.out.println(e);
			} 
		}

		for(Test t: L2) {
			try {
				t.join();
			}
			catch(Exception e) {
				System.out.println(e);
			} 
		}
    }
}
