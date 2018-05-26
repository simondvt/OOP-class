package clinic;

public class Patient {
	
	private String firstName;
	private String lastName;
	private String codFisc;
	
	public Patient(String first, String last, String SSN) {
		this.firstName = first;
		this.lastName = last;
		this.codFisc = SSN;
	}
	
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName + "(" + this.codFisc + ")";
	}
}
