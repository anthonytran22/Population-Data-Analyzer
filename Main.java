import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Main class or the "reporting program" for reading population file and displaying results.
 * 
 * @author Anthony Tran
 * @version 4/21/24
 *
 */
public class Main {

    public static void main(String[] args) {
        //Prompt user for input, then print header
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a list of population files: ");
        String popFile = input.nextLine();

        System.out.print("Enter a start year: ");
        int startY = Integer.parseInt(input.nextLine());

        System.out.println("Enter an end year: ");
        int endY = Integer.parseInt(input.nextLine());
        System.out.printf("%-24s%-13s%-11s%-6s%n", "State/County", startY, endY, "Growth");
        System.out.println("--------------- ------------ ------------ ------------");

        //Read state information from pop file
        List<String[]> stateFile = readState(popFile);
        //Iterate over each state
        for(String[] stateFiles : stateFile) {
            String stateN = stateFiles[0];
            String fileN = stateFiles[1];
            //Read pop data for the current state
            List<County> counties = readPopData(fileN, startY, endY);

            //Create state object to add counties
            State state = new SimpleState();
            state.setName(stateN);
            for(County county : counties) {
                state.addCounty(county);
            }
            //Call display results to output current state
            displayResults(state, startY, endY);
        }
    }

    /**
    * Reads state information from the pop file
    * @param popFile The file containing the state information
    * @return   A list of string arrays with state and file name
    */
    private static List<String[]> readState(String popFile) {
        List<String[]> stateFile = new ArrayList<>();

        // Use a try block so the scanner will auto close
        try(Scanner input = new Scanner(new File(popFile))) {
            while(input.hasNextLine()) {
                String line = input.nextLine();
                //Extract state name and file name
                String stateName = line.substring(0, line.indexOf(','));
                String fileName = line.substring(line.indexOf(',') + 1);

                //Create an array to store this information
                String[] parts = {stateName, fileName};
                //Add info to the list
                stateFile.add(parts);
            }
        } catch (IOException e) {
            System.err.println("Error" + e.getMessage());
        }
        //Return state info
        return stateFile;
    }
    /**
    * Reads population data from a file.
    * @param fileN  The file containing pop data
    * @param startY Start year indicated by user
    * @param endY   End year indicated by user
    * @return   List of county objects paired with population data
    */
    private static List<County> readPopData(String fileN, int startY, int endY) 
    {
        List<County> counties = new ArrayList<>();
        try(Scanner input = new Scanner(new File(fileN))) {
            //Skip the header line
            input.nextLine();
            while(input.hasNextLine()) {
                //Split each line of the file by comma to pull data
                String[] data = input.nextLine().split(",");
                String countyName = data[0];
                //Create county object and set the county name
                County county = new SimpleCounty();
                county.setName(countyName);

                //Start pairing county object with population data for that year
                for(int year = startY; year <= endY; year++) {
                    //Index is calculated between current and start year 
                    int population = Integer.parseInt(data[year - 2010 + 1]); 
                        county.setPopulation(year, population);
                    }
                    counties.add(county);
                }
            }  catch(IOException e) {
            System.err.println("Error");
        }
        return counties;
    }
    /**
    * Displays the results in a table
    * @param state  The state object containing pop data
    * @param startY The start year
    * @param endY   The end year
    */
    private static void displayResults(State state, int startY, int endY) {
        //Calculate and display state total stats
        int startPop = state.getPopulation(startY);
        int endPop = state.getPopulation(endY);
        int growth = endPop - startPop;
        System.out.println("");
        System.out.println("--------------- ------------ ------------ ------------");
        System.out.printf("%-18s%,10d%,13d%,+13d%n", state.getName(), startPop, endPop, growth);
        System.out.println("--------------- ------------ ------------ ------------");

        //Sort counties by end year population data and then display this data
        List<County> sortCounties = state.getCountiesByPopulation(endY);
        for(County county : sortCounties) {
            int countStart = county.getPopulation(startY);
            int countEnd = county.getPopulation(endY);
            int countGrowth = countEnd - countStart;
            System.out.printf("%3s%-15s%,10d%,13d%,+13d%n", "", county.getName(), countStart, countEnd, countGrowth);
        }

        //Calculate the average population of counties of both start and end year
        List<County> countiesAll = state.getCounties();
        int totalSPopulation = 0;
        int totalEPopulation = 0;
        for(County county : countiesAll) {
            totalSPopulation += county.getPopulation(startY);
        }
        for(County county : countiesAll) {
            totalEPopulation += county.getPopulation(endY);
        }
        int avgSPop = totalSPopulation / countiesAll.size();
        int avgEPop = totalEPopulation / countiesAll.size();
        
        //Calculate median for start year data and end year data
        List<County> sSortCounties = state.getCountiesByPopulation(startY);
        List<County> eSortCounties = state.getCountiesByPopulation(endY);
        int sizeS = sSortCounties.size();
        int sizeE = eSortCounties.size();
        int medIndS = sizeS / 2;
        int medIndE = sizeE / 2;
        int medPopS = 0;
        int medPopE = 0;
        
        if(sizeS % 2 == 0) {
            int medInd1S = medIndS - 1;
            medPopS = (sSortCounties.get(medInd1S).getPopulation(startY) +  sSortCounties.get(medIndS).getPopulation(startY)) / 2;
            
        } else {
            medPopS = sSortCounties.get(medIndS).getPopulation(startY);
        }

         if(sizeE % 2 == 0) {
            int medInd1E = medIndE - 1;
            medPopE = (eSortCounties.get(medInd1E).getPopulation(endY) + eSortCounties.get(medIndE).getPopulation(endY)) / 2;
            
        } else {
            medPopE = eSortCounties.get(medIndE).getPopulation(endY);
        }
        
        //Display last information
        System.out.println("--------------- ------------ ------------ ------------");
        System.out.printf("%3s%-18s%,7d%,13d%n", "", "Average pop.", avgSPop, avgEPop);
        System.out.printf("%3s%-18s%,7d%,13d%n", "", "Median pop.", medPopS, medPopE);

    }
}