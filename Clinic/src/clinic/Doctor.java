package clinic;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Doctor {

	private String firstName;
	private String lastName;
	private String codFisc;
	private String spec;
	
	private int id;
	
	private HashMap<String, Patient> patients = new HashMap<>();
	
	public Doctor(String first, String last, String ssn, int docID, String specialization) {
		this.firstName = first;
		this.lastName = last;
		this.codFisc = ssn;
		this.id = docID;
		this.spec = specialization;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSpecialization() {
		return spec;
	}
	
	public int getID() {
		return id;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void addPatient(String ssn, Patient p) {
		patients.put(ssn, p);
	}
	
	public Collection<String> getPatients() {
		return patients.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return this.lastName + " " + this.firstName + " (" + this.codFisc + ") [" 
				+ this.id + "]: " + this.spec;
	}
	
}
