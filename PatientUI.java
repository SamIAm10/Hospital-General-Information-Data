import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//UI for patient/consumer role
public class PatientUI extends JFrame {
	//obtain ordered state array from GenerateData for combobox selections
	private static String[] ordered_states = GenerateData.ordered_states;
	
	//obtain list of data maps from GenerateData for returning data to user
	private static DataMaps data_maps = GenerateData.data_maps;
	
    private String state = ordered_states[0]; //starting combobox selection
    
    //initialize this UI
	public static void main(String[] args) {
		PatientUI root = new PatientUI();
		root.setVisible(true);
	}
		
	//build the UI
    public PatientUI() {	
		this.setTitle("Patient/Consumer UI");
	    this.setSize(650, 150);
	    this.setLocation(400, 100);

	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBackground(Color.BLACK);
	    this.add(panel);

	    JLabel label = new JLabel("Select state/territory (ordered from highest to lowest average hospital rating):");
	    label.setFont(new Font("Arial", Font.BOLD, 16));
	    label.setAlignmentX(CENTER_ALIGNMENT);
	    label.setForeground(Color.RED);
	    panel.add(label);

	    //combobox for states
	    JComboBox<String> cb = new JComboBox<String>(ordered_states);
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

	    JButton btn = new JButton("Get list of all hospitals");
	    btn.setFont(new Font("Arial", Font.BOLD, 20));
	    btn.setAlignmentX(CENTER_ALIGNMENT);
	    btn.setBackground(Color.YELLOW);
	    btn.setForeground(Color.BLACK);
	    btn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//when button is pressed, create a new window with a list of hospitals in selected state
	    		JFrame frame = new JFrame("Hospitals in " + state + " ordered by descending rating");
	    	    frame.setSize(700, 500);
	    	    frame.setLocation(800, 100);

	    	    JPanel panel = new JPanel();
	    	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    	    frame.add(panel);
	    	    
	    	    //obtain info list for selected state from data_maps
	    	    ArrayList<ArrayList<String>> info_list = data_maps.getInfoMap().get(state);
	    	    
	    	    //convert 2D list to 2D array
	    		String[][] info_array = new String[info_list.size()][];
	    		for (int i = 0; i < info_list.size(); i++) {
	    		    ArrayList<String> row = info_list.get(i);
	    		    info_array[i] = row.toArray(new String[row.size()]);
	    		}
	    		
	    		//sort 2D array by 3rd column (rating)
	    		Arrays.sort(info_array, new Comparator<String[]>() {
	    			@Override
	    			public int compare(String[] row_1, String[] row_2) {
	    				//if no rating is available, set rating_1 and rating_2 to -1
	    				Integer rating_1 = (row_1[2].length() == 1) ? Integer.parseInt(row_1[2]) : -1;
	    				Integer rating_2 = (row_2[2].length() == 1) ? Integer.parseInt(row_2[2]) : -1;
	    				return rating_2.compareTo(rating_1);
	    			}
	    		});
	    		
	    	    String[] column_titles = {"Name of hospital", "City", "Rating"}; //column titles for table
	    		
	    		//create JTable for info_array
				JTable table = new JTable(info_array, column_titles);
				//set column widths
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.getColumnModel().getColumn(0).setPreferredWidth(400);
				table.getColumnModel().getColumn(1).setPreferredWidth(168);
				table.getColumnModel().getColumn(2).setPreferredWidth(100);
			    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
				table.setAlignmentX(CENTER_ALIGNMENT);
				table.setBackground(Color.BLACK);
				table.setForeground(Color.WHITE);
				panel.add(table);
				
				//add scroll bar to table in case it's too long
				JScrollPane scroll = new JScrollPane(table);
				panel.add(scroll);
				
				frame.setVisible(true);
	    	}
	    });
	    panel.add(btn);
	}
}
