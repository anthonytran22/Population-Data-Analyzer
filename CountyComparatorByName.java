/**
 * A Comparator for County objects in ascending order of name
 * 
 * @author Anthony Tran
 * @version 4/21/24
 */

import java.util.Comparator;

public class CountyComparatorByName implements Comparator<County> {
	

	@Override
	public int compare(County o1, County o2) {
		return o1.getName().compareTo(o2.getName());
	}
	
}