import java.util.LinkedHashMap;

class CSRW { //Critical Section Readers Writers
	
	int readers = 0;
	boolean writer = false;

	synchronized void enterWriter() {
		try {
			while(readers > 0 || writer)
				wait();

			writer = true;
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	synchronized void exitWriter() {
		writer = false;
		notifyAll();
	}

	synchronized void enterReader() {
		try {
			while(writer)
				wait();
			readers++;
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	synchronized void exitReader() {
		readers--;
		notify();
	}
}

public aspect LockAspect pertarget(reader(BinarySearchTree) || writer(BinarySearchTree)){
    

	CSRW locks;

	public LockAspect() {
		locks = new CSRW();;
	}

	pointcut reader(BinarySearchTree b):  target(b) && execution(* BinarySearchTree.lookup(*));

	pointcut writer(BinarySearchTree b) :  target(b) && (execution(* BinarySearchTree.insert(*)) || execution(* BinarySearchTree.remove(*)));


	before(BinarySearchTree b) : reader(b) {

		locks.enterReader();
		System.out.println("Reader entering for:"+b);
	}

	after(BinarySearchTree b) : reader(b) {

		//This is for testing purposes, you may comment it out
		try {
			Thread.sleep(500);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		locks.exitReader();
	}

	

	before(BinarySearchTree b) : writer(b) {

		locks.enterWriter();
		System.out.println("Writer entering for:"+b);
	}

	after(BinarySearchTree b) : writer(b) {
		
		//This is for testing purposes, you may comment it out
		try {
			Thread.sleep(500);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		locks.exitWriter();
	}
}
