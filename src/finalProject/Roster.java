package finalProject;

public class Roster {
	public String firstName;
	public int Id; 
	public String lastName;
	public String degree;
	public String graduateLevel;
	public String logIn;
	
	public Roster(int Id, String firstName, String lastName, String degree, String graduateLevel, String logIn){ 
		this.Id = Id;
		this.firstName = firstName; 
		this.lastName = lastName;
		this.degree = degree;
		this.graduateLevel = graduateLevel;
		this.logIn = logIn;
		}
	@Override
	public String toString() {
		return Id + firstName + lastName + degree + graduateLevel + logIn;
	}
}
