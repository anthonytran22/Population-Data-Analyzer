/**
 * A Comparator for County objects in descending order of population
 * 
 * @author Anthony Tran
 * @version 4/21/24
 */

import java.util.Comparator;

public class CountyComparatorByPopulation implements Comparator<County> {
	
	private int year;
	
	public CountyComparatorByPopulation(int year) {
		this.year = year;
	}

	@Override
	public int compare(County o1, County o2) {
		return o2.getPopulation(year) - o1.getPopulation(year);
	}
	
}