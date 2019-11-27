import java.lang.Thread;
import java.util.Random;
import java.util.ArrayList;

class Test extends Thread {

	BinarySearchTree<Integer> btree;

	public Test(String name, BinarySearchTree<Integer> btree) {
		super(name);
		this.btree = btree;
	}

	public void run() {

		Random random = new Random();
		for(int i = 0; i < 5; i++) {
			
			int action = random.nextInt(5);
			//Give readers more chances to spawn so that it is clear that many may enter at once
			if(action <= 2) {
				btree.lookup(random.nextInt(10));
			}
			else if(action == 3){
				btree.insert(random.nextInt(10));
			}
			else { //action == 4
				btree.remove(random.nextInt(10));
			}

		}

	}
}

public class Main {

    public static void main(String[] args) {
	
		//Create new trees
		BinarySearchTree<Integer> b1 = new BinarySearchTree<Integer>();
		BinarySearchTree<Integer> b2 = new BinarySearchTree<Integer>();

		ArrayList<Test> L1 = new ArrayList<Test>();
		//Create 4 threads that will run on the first tree
		for(int i = 0; i < 4; i++) {
			L1.add(new Test("Thread #"+Integer.toString(i), b1));
		}

		ArrayList<Test> L2 = new ArrayList<Test>();
		//Create 4 threads that will run on the second tree
		for(int i = 4; i < 8; i++) {
			L2.add(new Test("Thread #"+Integer.toString(i), b2));
		}

		//Start all 8 threads
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
