import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//main UI for both roles
public class MainUI extends JFrame {
	//initialize main UI
	public static void main(String[] args) {
		MainUI main = new MainUI();
		main.setVisible(true);
	}
	
	//build the UI
	private MainUI() {
		this.setTitle("Main UI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //terminate program on close
	    this.setSize(300, 170);
	    this.setLocation(100, 100);

	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBackground(Color.BLACK);
	    this.add(panel);

	    JLabel label = new JLabel("Select role:");
	    label.setFont(new Font("Arial", Font.BOLD, 20));
	    label.setAlignmentX(CENTER_ALIGNMENT);
	    label.setForeground(Color.RED);
	    panel.add(label);
	    
	    JButton btn1 = new JButton("Statistical Analyst");
	    btn1.setFont(new Font("Arial", Font.BOLD, 20));
	    btn1.setAlignmentX(CENTER_ALIGNMENT);
	    btn1.setBackground(Color.YELLOW);
	    btn1.setForeground(Color.BLACK);
	    btn1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//run analyst UI if btn1 is pressed
	    		AnalystUI analyst = new AnalystUI();
	    		analyst.setVisible(true);
	    	}
	    });
	    panel.add(btn1);
	    
	    JButton btn2 = new JButton("Patient/Consumer");
	    btn2.setFont(new Font("Arial", Font.BOLD, 20));
	    btn2.setAlignmentX(CENTER_ALIGNMENT);
	    btn2.setBackground(Color.YELLOW);
	    btn2.setForeground(Color.BLACK);
	    btn2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//run patient UI if btn2 is pressed
	    		PatientUI patient = new PatientUI();
	    		patient.setVisible(true);
	    	}
	    });
	    panel.add(btn2);
	}
}
