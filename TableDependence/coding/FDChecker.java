import java.util.*;

public class FDChecker {

	/**
	 * Checks whether a decomposition of a table is dependency
	 * preserving under the set of functional dependencies fds
	 * 
	 * @param t1 one of the two tables of the decomposition
	 * @param t2 the second table of the decomposition
	 * @param fds a complete set of functional dependencies that apply to the data
	 * 
	 * @return true if the decomposition is dependency preserving, false otherwise
	 **/
	public static boolean checkDepPres(AttributeSet t1, AttributeSet t2, Set<FunctionalDependency> fds) {
		//your code here
		//a decomposition is dependency preserving, if local functional dependencies are
		//sufficient to enforce the global properties
		//To check a particular functional dependency a -> b is preserved, 
		//you can run the following algorithm
		//result = a
		//while result has not stabilized
		//	for each table in the decomposition
		//		t = result intersect table 
		//		t = closure(t) intersect table
		//		result = result union t
		//if b is contained in result, the dependency is preserved
		
		Set<FunctionalDependency> allDep = new HashSet<FunctionalDependency>(fds);

		Iterator fdI  = allDep.iterator();
		AttributeSet result;
		AttributeSet prevResult;
		FunctionalDependency fd;
		AttributeSet temp;

		AttributeSet attrs1 = new AttributeSet(t1);
		AttributeSet attrs2 = new AttributeSet(t2);

		boolean change = true;
		//loops through set of functional dependencies 
		while(fdI.hasNext()){
			fd = (FunctionalDependency)fdI.next();
			result = fd.left;
			change = true;
			//loops if the result was chanfes from the previous loop
			while(change){
				prevResult = result;
				temp = new AttributeSet(result);
				temp.retainAll(attrs1);
				temp = closure(temp, allDep);
				temp.retainAll(attrs1);
				result.addAll(temp);


				temp = new AttributeSet(result);
				temp.retainAll(attrs2);
				temp = closure(temp, allDep);
				temp.retainAll(attrs2);
				result.addAll(temp);

				if (result==prevResult){
					change=false;
				}

			}

			if(!result.contains(fd.right)){
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether a decomposition of a table is lossless
	 * under the set of functional dependencies fds
	 * 
	 * @param t1 one of the two tables of the decomposition
	 * @param t2 the second table of the decomposition
	 * @param fds a complete set of functional dependencies that apply to the data
	 * 
	 * @return true if the decomposition is lossless, false otherwise
	 **/
	public static boolean checkLossless(AttributeSet t1, AttributeSet t2, Set<FunctionalDependency> fds) {
		//your code here
		//Lossless decompositions do not lose information, the natural join is equal to the 
		//original table.
		//a decomposition is lossless if the common attributes for a superkey for one of the
		//tables.
		

		AttributeSet nT1 = closure(t1,fds);
		AttributeSet nT2 = closure(t2,fds);
		AttributeSet nT3 = new AttributeSet(nT1);
		nT3.retainAll(nT2);

		nT3 = closure (nT3, fds);
			
		//check if closure contains either all items in t1 or t2
		if(nT3.containsAll(t1)|| nT3.containsAll(t2)){
			return true; 
		}

		return false;
		
	}

		
	

	//recommended helper method
	//finds the total set of attributes implied by attrs
	public static AttributeSet closure(AttributeSet attrs, Set<FunctionalDependency> fds) {
		Set<FunctionalDependency> allDep = new HashSet<FunctionalDependency>(fds);
		FunctionalDependency aDep;
		Iterator aFds = allDep.iterator();

		AttributeSet nAttrs = new AttributeSet(attrs);

		AttributeSet oAttrs=null;

		while(oAttrs!=nAttrs){
			oAttrs=nAttrs;
			while(aFds.hasNext()){ 
				aDep = (FunctionalDependency) aFds.next();
				System.out.println(aDep.left);
				if(nAttrs.containsAll(aDep.left)){
					nAttrs.add(aDep.right);
					}
			}
		}
		return nAttrs;
	}
}



