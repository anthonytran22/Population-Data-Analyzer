import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Implementation of the state methods as specified in the state class
 * 
 * @author Anthony Tran
 * @version 4/21/24
 *
 */

public class SimpleState implements State {
	private String name;
    private List<County> counties;
	
	public SimpleState() {
		this.name = "";
        this.counties = new ArrayList<County>();
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void addCounty(County county) {
		counties.add(county);
	}
	
	@Override
	public County getCounty(String name) {
        County foundCounty = null;
        //Iterate through the list of counties to find a match
        for(County county : counties) {
            if(county.getName().equals(name)) {
                foundCounty = county;
            }
        }
        return foundCounty;
	}
	
	@Override
	public int getPopulation(int year) {
	    int totalPopulation = 0;
        //Iterate through the list of counties
        for(County county : counties) {
    //Calculate total population by adding all the county populations of that year
               totalPopulation += county.getPopulation(year);
       }
       return totalPopulation;
	}
	
	@Override
	public List<County> getCounties() {
        List<County> sortCounties = new ArrayList<>(counties);
		CountyComparatorByName comp = new CountyComparatorByName();
//Use the assignment hints to sort the counties using the comparator
        Collections.sort(sortCounties, comp);
        return sortCounties;
	}
	
	@Override
	public List<County> getCountiesByPopulation(int year) {
        List<County> sortCounties = new ArrayList<>(counties);
		CountyComparatorByPopulation comp = new CountyComparatorByPopulation(year);
//Use the assignment hints to sort the counties using the comparator
        Collections.sort(sortCounties, comp);
        return sortCounties;
	}
	

}
