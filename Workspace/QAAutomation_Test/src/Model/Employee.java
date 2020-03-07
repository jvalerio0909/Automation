package Model;

/*
 * Created by: Juliana Valerio Villalobos. Date: 04/03/2020.
 */

public class Employee {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String identification;
    private String email;
    private String leaderName;
    private String date;
	
	public Employee(){
		this.setFirstName("");
		this.setLastName("");
		this.setPhoneNumber("");
		this.setIdentification("");
        this.setEmail("");
        this.setLeaderName("");
        this.setDate("");
	}
	
	public Employee(String firstN,String lastN, String phone, String address, String email, String lead, String date){
		this.setFirstName(firstN);
		this.setLastName(lastN);
		this.setPhoneNumber(phone);
		this.setIdentification(address);
        this.setEmail(email);
        this.setLeaderName(lead);
        this.setDate(date);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
    }
    
    public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
    }
	
	public String toString(){
        return "First name: " + this.firstName + ", Last name: " + this.lastName + 
        ", Phone number: " + this.phoneNumber + ", Identificator: " + this.identification + 
        ", Email: " + this.email + ", Leader Name: " + this.leaderName + ", Date: " + this.date;
		
	}

}
