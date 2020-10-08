import java.util.*;

//class to store multiple types of data maps and return them in GenerateData
public class DataMaps {
	private LinkedHashMap<String, Integer> numratings_map = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Double> avg_map = new LinkedHashMap<String, Double>();
	private LinkedHashMap<String, Double> stdev_map = new LinkedHashMap<String, Double>();
    private LinkedHashMap<String, ArrayList<ArrayList<String>>> info_map = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
    
    public DataMaps (LinkedHashMap<String, Integer> numratings_map,
    		LinkedHashMap<String, Double> avg_map,
    		LinkedHashMap<String, Double> stdev_map,
    		LinkedHashMap<String, ArrayList<ArrayList<String>>> info_map) {
    	this.numratings_map = numratings_map;
    	this.avg_map = avg_map;
    	this.stdev_map = stdev_map;
    	this.info_map = info_map;
    }
    
    public LinkedHashMap<String, Integer> getNumratingsMap() {
    	return this.numratings_map;
    }
    
    public LinkedHashMap<String, Double> getAvgMap() {
    	return this.avg_map;
    }
    
    public LinkedHashMap<String, Double> getStdevMap() {
    	return this.stdev_map;
    }
    
    public LinkedHashMap<String, ArrayList<ArrayList<String>>> getInfoMap() {
    	return this.info_map;
    }
}
