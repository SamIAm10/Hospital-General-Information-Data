import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//UI for statistical analyst role
public class AnalystUI extends JFrame {
	//obtain state array from GenerateData for combobox selections
	private static String[] states = GenerateData.states;
	
	//obtain list of data maps from GenerateData for returning data to user
	private static DataMaps data_maps = GenerateData.data_maps;
	
    private String state = states[0]; //starting combobox selection
    
    //initialize this UI
	public static void main(String[] args) {
		AnalystUI root = new AnalystUI();
		root.setVisible(true);
	}
	
	//build the UI
	public AnalystUI() {	
		this.setTitle("Statistical Analyst UI");
	    this.setSize(500, 220);
	    this.setLocation(400, 100);

	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBackground(Color.BLACK);
	    this.add(panel);

	    JLabel label = new JLabel("Select state/territory:");
	    label.setFont(new Font("Arial", Font.BOLD, 20));
	    label.setAlignmentX(CENTER_ALIGNMENT);
	    label.setForeground(Color.RED);
	    panel.add(label);

	    //combobox for states
	    JComboBox<String> cb = new JComboBox<String>(states);
	    cb.setFont(new Font("Arial", Font.BOLD, 20));
	    cb.setMaximumSize(cb.getPreferredSize());
	    cb.setAlignmentX(CENTER_ALIGNMENT);
	    cb.setForeground(Color.WHITE);
	    cb.setBackground(Color.DARK_GRAY);
	    cb.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//when a state is selected in combobox, reset "state" variable to new selection
	    	    state = (String)cb.getSelectedItem();
	    	}
	    });
	    panel.add(cb);
	    
	    //label for number of valid ratings
	    JLabel num_ratings = new JLabel();
	    num_ratings.setFont(new Font("Arial", Font.BOLD, 20));
	    num_ratings.setAlignmentX(CENTER_ALIGNMENT);
	    num_ratings.setForeground(Color.WHITE);
	    panel.add(num_ratings);
	    
	    //label for average
	    JLabel avg = new JLabel();
	    avg.setFont(new Font("Arial", Font.BOLD, 20));
	    avg.setAlignmentX(CENTER_ALIGNMENT);
	    avg.setForeground(Color.WHITE);
	    panel.add(avg);
	    
	    //label for standard deviation
	    JLabel stdev = new JLabel();
	    stdev.setFont(new Font("Arial", Font.BOLD, 20));
	    stdev.setAlignmentX(CENTER_ALIGNMENT);
	    stdev.setForeground(Color.WHITE);
	    panel.add(stdev);

	    JButton btn = new JButton("Find data for all hospitals");
	    btn.setFont(new Font("Arial", Font.BOLD, 20));
	    btn.setAlignmentX(CENTER_ALIGNMENT);
	    btn.setBackground(Color.YELLOW);
	    btn.setForeground(Color.BLACK);
	    btn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//when button is pressed, obtain data for selected state and set JLabels to data outputs
				double[] data = findData(state);
				if (data[0] == 0) {
					num_ratings.setText("No data available for " + state + ".");
					avg.setText("");
					stdev.setText("");
				}
				else {
					num_ratings.setText("Number of recorded hospital ratings in " + state + ": " + (int)data[0]);
					avg.setText("Average rating: " + data[1]);
					stdev.setText("Standard deviation: " + data[2]);
				}
	    	}
	    });
	    panel.add(btn);
	}
	
	//private function for returning a double array of data for a state from data_maps
	private double[] findData(String state) {
		double num_ratings = data_maps.getNumratingsMap().get(state);
		double avg = data_maps.getAvgMap().get(state);
		double stdev = data_maps.getStdevMap().get(state);
		double[] data = {num_ratings, avg, stdev};
		return data;
	}
}
