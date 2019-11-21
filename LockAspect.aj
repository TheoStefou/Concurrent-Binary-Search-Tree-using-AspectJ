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

public aspect LockAspect {
    

	LinkedHashMap<BinarySearchTree, CSRW> CSMap;

	public LockAspect() {
		CSMap = new LinkedHashMap<BinarySearchTree, CSRW>();
	}

	Object around() : call(BinarySearchTree.new()) {
		Object o = proceed();
		CSMap.put((BinarySearchTree)o, new CSRW());
		return o;
	}

	pointcut reader(BinarySearchTree b): target(b) && call(* BinarySearchTree.lookup(*));

	pointcut writer(BinarySearchTree b) : 	target(b)
										&& (call(* BinarySearchTree.insert(*))
										|| 	call(* BinarySearchTree.remove(*)));


	before(BinarySearchTree b) : reader(b) {

		CSRW c = CSMap.get(b);
		c.enterReader();
	}

	after(BinarySearchTree b) : reader(b) {

		CSRW c = CSMap.get(b);
		c.exitReader();
	}

	

	before(BinarySearchTree b) : writer(b) {

		CSRW c = CSMap.get(b);
		c.enterWriter();
	}

	after(BinarySearchTree b) : writer(b) {
		CSRW c = CSMap.get(b);
		c.exitWriter();
	}
}
