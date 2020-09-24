import java.util.*;

//class to store ratings and calculate data
public class ListData<E> extends ArrayList<E> implements List<E> {
	
	//find average
	public double getAverage() { 
		double sum = 0;
		for (E rating : this)
			sum += (int)rating; //cast generic type "rating" to integer
		return sum/this.size();
	}
	
	//find standard deviation
	public double getStandardDev() { 
		double stdev = 0;
		double avg = this.getAverage();
		for (E rating : this)
			stdev += Math.pow((int)rating - avg, 2);
		return Math.sqrt(stdev/this.size());
	}
}
