import java.io.*;
import java.util.*;

//class to generate data from HGI data set to be used by statistical analyst and consumer/patient roles
public class GenerateData {
	//public string array of all possible state inputs according to the HGI data set
	public static String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO",
			"CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
    	    "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE",
    	    "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA",
    	    "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
    	    "WI", "VI", "WY", "AS", "GU", "MP"}; 
			
	//public DataMaps object of data maps
	public static DataMaps data_maps = getDataMaps();
	
	//public string array of all possible state inputs in decreasing average hospital rating order
	public static String[] ordered_states = generateOrderedStateArray();
	
	//function to return a DataMaps object consisting of four maps: 
	//*state to number of available hospital ratings
	//*state to average hospital rating
	//*state to standard deviation of ratings
	//*state to 2D list of all hospital names/cities/ratings in that state
	private static DataMaps generateDataMaps() throws FileNotFoundException {
		String filename = "HospitalGeneralInformation.csv";
		File file = new File(filename);
		
	    LinkedHashMap<String, Integer> state_num_ratings = new LinkedHashMap<String, Integer>();
	    LinkedHashMap<String, Double> state_avg_ratings = new LinkedHashMap<String, Double>();
	    LinkedHashMap<String, Double> state_stdev = new LinkedHashMap<String, Double>();
	    LinkedHashMap<String, ArrayList<ArrayList<String>>> state_info = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
		
	    //loop to populate the maps with data for each state
		for (int state_index = 0; state_index < states.length; state_index++) {
			Scanner scanner = new Scanner(file);
			scanner.nextLine(); //skip first row
			ListData<Integer> ratings = new ListData<Integer>();
			ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
			//while loop for reading each line in the csv and populating the data structures for the current state
			while (scanner.hasNext()) { 
				String line = scanner.nextLine();
				//continue to read data only if the current line is for the current state
				if (line.contains(states[state_index])) {
					//create a line list of all values split by commas (except commas in quotes)
					ArrayList<String> linelist = new ArrayList<String>();
					boolean in_quotes = false;
					StringBuilder sb = new StringBuilder();
					char[] char_array = line.toCharArray();
					//check each character in line for commas and quotes
					for (char c : char_array) { 
					    if (c == ',') {
					        if (in_quotes) 
					        	sb.append(c);
					        else {
					            linelist.add(sb.toString());
					            sb = new StringBuilder();
					        }
					    }
					    else if (c == '\"') 
					    	in_quotes = !in_quotes;
					    else 
					    	sb.append(c);
					}
					linelist.add(sb.toString());
					
					//if current line is for current state, add hospital name/city/rating to 2D info list
					if (states[state_index].equals(linelist.get(4))) {
						info.add(new ArrayList<String>());
						int info_index = info.size() - 1;
						info.get(info_index).add(linelist.get(1));
						info.get(info_index).add(linelist.get(3));
						info.get(info_index).add(linelist.get(12));
					}
					
					if (states[state_index].equals(linelist.get(4)) && linelist.get(12).length() == 1) 
						//add rating only if it's for the current state and if it's available
						ratings.add(Integer.parseInt(linelist.get(12)));
				}
			}
			scanner.close();
			
			//map current state to 2D info list
			state_info.put(states[state_index], info);
			
			//map current state to number of hospital ratings, average hospital rating, and standard deviation
			if (ratings.size() > 0) {
				state_num_ratings.put(states[state_index], ratings.size());
				state_avg_ratings.put(states[state_index], ratings.getAverage());
				state_stdev.put(states[state_index], ratings.getStandardDev());
			}
			else { //if no ratings are available for state, set average rating and standard deviation to -1.0
				state_num_ratings.put(states[state_index], 0);
				state_avg_ratings.put(states[state_index], -1.0);
				state_stdev.put(states[state_index], -1.0);
			}
		}
				
		//populate the DataMaps object and return it
		DataMaps data = new DataMaps(state_num_ratings, state_avg_ratings, state_stdev, state_info);
		return data;
	}
	
	//private function for returning DataMaps object of data maps with try-catch clauses
	private static DataMaps getDataMaps() {
		try {
			DataMaps data_maps = generateDataMaps();
			return data_maps;
		} 
		catch (FileNotFoundException e) {
			return null;
		}
	}
	
	//function to return a string array of states ordered in descending average hospital rating
	private static String[] generateOrderedStateArray() {
		LinkedHashMap<String, Double> ordered_state_avg_ratings_map = sortByDescendingValues(data_maps.getAvgMap());
		String[] states_by_desending_rating = ordered_state_avg_ratings_map.keySet()
				.toArray(new String[ordered_state_avg_ratings_map.size()]);
		return states_by_desending_rating;
	}
	
	//function to return a map sorted by descending value
	private static LinkedHashMap<String, Double> sortByDescendingValues(LinkedHashMap<String, Double> hashmap) {
		LinkedHashMap<String, Double> sorted = new LinkedHashMap<String, Double>();
		hashmap.entrySet().stream()
	    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
	    .forEachOrdered(element -> sorted.put(element.getKey(), element.getValue()));
		return sorted;
	}
}
