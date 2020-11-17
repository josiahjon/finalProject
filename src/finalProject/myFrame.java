package finalProject;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JFileChooser;

public class myFrame extends JFrame implements ActionListener{
	
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenu aboutMenu;
	JMenuItem loadARoster;
	JMenuItem addAttendance;
	JMenuItem save;
	JMenuItem plotData;
	JMenuItem aboutTeam;
	File file;
	Scanner fileIn;
	int response;
	JFileChooser chooser = new JFileChooser("");
	public static final String delimiter = ",";
	
	myFrame(){
		//setup for the menu goes here
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900,900);
		this.setLayout(new FlowLayout());
		
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		aboutMenu = new JMenu("About");
		
		loadARoster = new JMenuItem("Load a Roster");
		save = new JMenuItem("Save");
		addAttendance = new JMenuItem("Add Attendance");
		plotData = new JMenuItem("Plot Data");
		
		aboutTeam = new JMenuItem("About Team");
		
		//action listeners for when user clicks on the menu
		loadARoster.addActionListener(this);
		addAttendance.addActionListener(this);
		save.addActionListener(this);
		plotData.addActionListener(this);
		aboutTeam.addActionListener(this);
		
		fileMenu.add(loadARoster);
		fileMenu.add(addAttendance);
		fileMenu.add(save);
		fileMenu.add(plotData);	
		aboutMenu.add(aboutTeam);
		
		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);
		
		this.setJMenuBar(menuBar);
		
		this.setVisible(true);
	}
	
	//the arrayList that will hold the roster
	List<Roster> roster = new ArrayList<>();
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//when user selects load roster. User selects csv file then it gets placed into the arraylist "roster"
		if(e.getSource()==loadARoster) {
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			response = chooser.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				try {
					
			         FileReader fr = new FileReader(file);
			         BufferedReader br = new BufferedReader(fr);
			         String line = br.readLine();
			         
			         while(line != null) {
			            String[] tempArray = line.split(delimiter);
			            Roster roster1 = createRoster(tempArray);
			            roster.add(roster1);
			            line = br.readLine();
			         }
				br.close();
				} catch (FileNotFoundException f) {
					f.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		
		}
		//for when the user clicks on the other menu options
		if(e.getSource()==addAttendance) {
			System.out.println("clicked attendance");
		}
		if(e.getSource()==save) {
			System.out.println("clicked save");
		}
		if(e.getSource()==plotData) {
			System.out.println("clicked plotdata");
		}
		if(e.getSource()==aboutTeam) {
			//need to gather teams info then update this 
			JOptionPane.showMessageDialog(aboutTeam, "Here is where the info about the team goes");
		}
	}

	private static Roster createRoster(String[] tempArray) {
		int Id = Integer.parseInt(tempArray[0]);
		String firstName = tempArray[1];
		String lastName = tempArray[2];
		String degree = tempArray[3];
		String graduateLevel = tempArray[4];
		String logIn = tempArray[5];
		return new Roster(Id, firstName, lastName, degree, graduateLevel, logIn);
	}
	
	
}