import java.util.HashMap;
/**
 * Implementation of the county methods as specified in the county class
 * 
 * @author Anthony Tran
 * @version 4/21/24
 *
 */

public class SimpleCounty implements County {
	private String name;
    private HashMap<Integer, Integer> populationData;
	
	public SimpleCounty() {
		populationData = new HashMap<Integer, Integer>();
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
	public void setPopulation(int year, int pop) {
		populationData.put(year, pop);
	}
	
	@Override
	public int getPopulation(int year) {
		Integer population = populationData.get(year);
        int result = 0;
        //Check if population data for that year exists
        if(population != null) {
            result = population;
        }
        return result;
	}

    @Override
    public String toString() {
       return name;


    }
}