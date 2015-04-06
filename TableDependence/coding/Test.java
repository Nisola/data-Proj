
import static org.junit.Assert.*;
import java.util.*;

public class Test {

	@org.junit.Test
	public void depPresBasictest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t2.add(new Attribute("b"));
		
		fds.add(new FunctionalDependency(t1,new Attribute("a")));

		// tables
		// a
		// b
		// fds
		// a -> a
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
		
		
		fds.add(new FunctionalDependency(t1, new Attribute("b")));
		// tables
		// a
		// b
		// fds
		// a -> a
		// a -> b
		assertFalse(FDChecker.checkDepPres(t1, t2, fds));
	}

	@org.junit.Test
	public void losslessBasictest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t2.add(new Attribute("b"));
		
		// tables
		// a
		// b
		// fds

		assertFalse(FDChecker.checkLossless(t1, t2, fds));
		
		t1.add(new Attribute("b"));
		// tables
		// a b
		// b
		// fds
		assertTrue(FDChecker.checkLossless(t1, t2, fds));

	}
	
	@org.junit.Test
	public void depPresFDtest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t1.add(new Attribute("b"));
		t2.add(new Attribute("b"));
		
		fds.add(new FunctionalDependency(t1,new Attribute("b")));

		// tables
		// a b
		// b
		// fds
		// ab -> b
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
		
		
		fds.add(new FunctionalDependency(t2, new Attribute("a")));
		// tables
		// a b
		// b
		// fds
		// ab -> b
		// b -> a
		assertTrue(FDChecker.checkDepPres(t1, t2, fds));
	}

	@org.junit.Test
	public void losslesstest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t1.add(new Attribute("b"));
		t2.add(new Attribute("b"));
		t2.add(new Attribute("c"));
		t2.add(new Attribute("d"));
		
		AttributeSet temp = new AttributeSet();
		temp.add(new Attribute("b"));
		fds.add(new FunctionalDependency(temp,new Attribute("c")));
		// tables
		// a b
		// b c d
		// fds
		// b -> c
		assertFalse(FDChecker.checkLossless(t1, t2, fds));
		
		fds.add(new FunctionalDependency(temp, new Attribute("d")));
		// tables
		// a b
		// b c d
		// fds
		// b -> c
		// c -> d
		assertTrue(FDChecker.checkLossless(t1, t2, fds));
		
	}

	@org.junit.Test
	public void closeTest() {
		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		

		t2.add(new Attribute("b"));
		t2.add(new Attribute("i"));
		
		
		AttributeSet temp = new AttributeSet();
		AttributeSet temp1 = new AttributeSet();
		AttributeSet temp2 = new AttributeSet();
		AttributeSet temp3 = new AttributeSet();
		AttributeSet temp4 = new AttributeSet();

		temp.add(new Attribute("b"));
		temp.add(new Attribute("a"));

		temp1.add(new Attribute("d"));
		temp1.add(new Attribute("c"));

		temp2.add(new Attribute("e"));
		



		fds.add(new FunctionalDependency(t1,new Attribute("d")));
		fds.add(new FunctionalDependency(temp ,new Attribute("e")));
		fds.add(new FunctionalDependency(t2,new Attribute("e")));
		fds.add(new FunctionalDependency(temp1,new Attribute("i")));
		fds.add(new FunctionalDependency(temp2,new Attribute("c")));
		
		// tables
		// a b
		// b c d

		temp3.add(new Attribute("a"));
		temp3.add(new Attribute("e"));


		temp4.add(new Attribute("a"));
		temp4.add(new Attribute("c"));
		temp4.add(new Attribute("d"));
		temp4.add(new Attribute("e"));
		temp4.add(new Attribute("i"));
		


		/*
		a->d
		ab-e
		bi-e
		cd-i
		e-c

		a-e

		acdei*/
		System.out.println(t1);
		System.out.println(temp);
		System.out.println(t2);	
		System.out.println(temp1);
		System.out.println(temp2);
		System.out.println(temp3);


		// fds
		// b -> c

		assertEquals(FDChecker.closure(temp3, fds),temp4);
	}

	@org.junit.Test
	public void closedTest() {

		AttributeSet t1 = new AttributeSet();
		AttributeSet t2 = new AttributeSet();
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		
		t1.add(new Attribute("a"));
		t1.add(new Attribute("g"));
		
		
		t2.add(new Attribute("b"));
		t2.add(new Attribute("c"));
		
		AttributeSet temp = new AttributeSet();
		temp.add(new Attribute("a"));
		fds.add(new FunctionalDependency(temp,new Attribute("b")));
		
		AttributeSet temp1 = new AttributeSet();
		temp1.add(new Attribute("a"));


		AttributeSet temp2 = new AttributeSet();
		temp2.add(new Attribute("c"));
		temp2.add(new Attribute("g"));

	

		AttributeSet temp3 = new AttributeSet();
		temp3.add(new Attribute("c"));
		temp3.add(new Attribute("g"));

		AttributeSet temp4 = new AttributeSet();
		temp4.add(new Attribute("b"));

		AttributeSet temp5 = new AttributeSet();
		temp5.add(new Attribute("a"));
		temp5.add(new Attribute("b"));
		temp5.add(new Attribute("c"));
		temp5.add(new Attribute("g"));
		temp5.add(new Attribute("h"));
		temp5.add(new Attribute("i"));

		fds.add(new FunctionalDependency(temp1,new Attribute("c")));
		fds.add(new FunctionalDependency(temp2,new Attribute("h")));
		fds.add(new FunctionalDependency(temp3,new Attribute("i")));
		fds.add(new FunctionalDependency(temp4,new Attribute("h")));
		//fds.add(new FunctionalDependency(temp5,new Attribute("a")));
		// tables
		// b c d
		// a c e
		// fds
		// ab -> c
		// c -> e
		// b -> d
		// e -> a
		


		assertEquals(FDChecker.closure(t1,fds), temp5 );

	}


	public static void main(String[] args) {
		Test tests = new Test();

		tests.closedTest();

		tests.depPresFDtest();
		tests.losslesstest();
		tests.depPresBasictest();
		tests.losslessBasictest();




	}
}
