package finalProject;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class myFrame extends JFrame implements ActionListener{
	
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenu aboutMenu;
	JMenuItem loadARoster;
	JMenuItem addAttendance;
	JMenuItem save;
	JMenuItem plotData;
	JMenuItem aboutTeam;
	DefaultTableModel table;
	JTable rosterTable;
	JScrollPane rosterScrollPane;
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
		
		String columnNames[] = {"ID", "First Name", "Last Name", "Program", "Level", "ASURITE"};
		table = new DefaultTableModel(columnNames, 0);
		rosterTable = new JTable(table);
		rosterScrollPane = new JScrollPane(rosterTable);
		
		this.setVisible(true);
	}
	
	//the arrayList that will hold the roster
	List<Roster> roster = new ArrayList<>();
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//when user selects load roster. User selects csv file then it gets placed into the arraylist "roster"
		if(e.getSource() == loadARoster) {
			loadRoster();
		}
		
		//for when the user clicks on the other menu options
		if(e.getSource() == addAttendance) {
			createAttendance();
		}
		if(e.getSource() == save) {
			saveRoster();
		}
		if(e.getSource() == plotData) {
			System.out.println("clicked plotdata");
		}
		if(e.getSource() == aboutTeam) {
			//need to gather teams info then update this 
			JOptionPane.showMessageDialog(aboutTeam, "Members of the team: Josiah Talebi, (insert names here)");
		}
	}

	private void loadRoster(){
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
			createRosterTable(roster);
			} catch (FileNotFoundException f) {
				f.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
	
	private void createRosterTable(List<Roster> roster) {
		
		add(rosterScrollPane);
		for(int i = 0; i < roster.size(); i++) {
			String id = "" + roster.get(i).getId();
			String firstName = roster.get(i).getFirstName();
			String lastName = roster.get(i).getLastName();
			String degree = roster.get(i).getDegree();
			String gradLevel = roster.get(i).getGraduateLevel();
			String login = roster.get(i).getLogIn();
			
			Object[] data = {id, firstName, lastName, degree, gradLevel, login};
			table.addRow(data);
		}
		revalidate();
	}
	
	private int loadAttendance(int attendanceDays, String month, String day) {
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		response = chooser.showOpenDialog(null);
		if(response == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			int unlistedStudents = 0;
			try {
				
		         FileReader fr = new FileReader(file);
		         BufferedReader br = new BufferedReader(fr);
		         String line = br.readLine();
		         
		         this.table.addColumn(month + " " + day);
		 		 for(int i = 0; i < table.getRowCount(); i++) 
		 			table.setValueAt(0, i, attendanceDays + 5);
		         
		         while(line != null) {
		            String[] tempArray = line.split(delimiter);
		            
		        	String asurite = tempArray[0];
		        	int minutes = Integer.parseInt(tempArray[1]);
		        	boolean studentFound = false;
		            for(int i = 0; i < table.getRowCount(); i++) {
		            	if(table.getValueAt(i, 5).equals(asurite)) {
		            		int attendTime = (int) table.getValueAt(i, attendanceDays + 5);
		            		table.setValueAt(attendTime + minutes, 
		            				i, attendanceDays + 5);
		            		studentFound = true;
		            	}
		            }
		            if (!studentFound)
		            	unlistedStudents++;
		            
		            line = br.readLine();
		         }
			br.close();
			return unlistedStudents;
			} catch (FileNotFoundException f) {
				f.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return -1;
	}
	
	private void createAttendance(/*String month, String day*/) {
		int attendanceDays = table.getColumnCount() - 6;
		Object[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
		String month = (String)JOptionPane.showInputDialog(this, "Choose a Month", "Select Month",
				JOptionPane.PLAIN_MESSAGE, null, months, "Jan");
		String day;
		Object[] days = new Object[31];
		
		if (month == null)
			return;
		
		else if (month.matches("Feb")) {
			for (int i = 1; i <= 29; i++)
				days[i-1] = "" + i;
		}
		else if (month.matches("Apr|Jun|Sept|Nov")){
			for (int i = 1; i <= 30; i++)
				days[i-1] = "" + i;
		}
		else {
			for (int i = 1; i <= 31; i++)
				days[i-1] = "" + i;
		}
		
		day = (String)JOptionPane.showInputDialog(this, "Choose a Day", "Select Day",
				JOptionPane.PLAIN_MESSAGE, null, days, "1");
			
		if (day == null) 
			return;
		
		attendanceDays++;
		int unlistedStudents = loadAttendance(attendanceDays, month, day);
		
		if (unlistedStudents == -1)
			JOptionPane.showMessageDialog(this, "There was an error loading your file", "File Read Error", JOptionPane.ERROR_MESSAGE);
		else {
			String attendees = "";
			int studentTotal = unlistedStudents;
			for(int i = 0; i < table.getRowCount(); i++) {
				if(!table.getValueAt(i, attendanceDays + 5).equals("0"))
					studentTotal++;
					attendees += table.getValueAt(i, 1) + " " + table.getValueAt(i, 2) + ", connected for " + table.getValueAt(i, attendanceDays + 5) + " minute(s).\n";
			}
			JOptionPane.showMessageDialog(this, "Data loaded for " + studentTotal + " user(s) in the roster.\n" + 
											unlistedStudents + " additional attendee(s) was found.\n" + attendees);
		}
			
	}
	
	private void saveRoster() {
		
		String userInput = JOptionPane.showInputDialog("Type in a file name (be sure to add .csv): ");
		try
		{
			FileWriter csvFile = new FileWriter(userInput);
			for(int i = -1; i < table.getRowCount(); i++)
			{
				for(int j = 0; j < table.getColumnCount(); j++)
				{
					if(i == -1)
						csvFile.append(String.valueOf(table.getColumnName(j)));
					else
						csvFile.append(String.valueOf(table.getValueAt(i, j)));
					csvFile.append(delimiter);
						
				}
				csvFile.append("\n");
			}
			csvFile.flush();
			csvFile.close();
			
			}
			catch(FileNotFoundException f)
			{
				f.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
	}
	
}