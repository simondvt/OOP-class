package clinic;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {

	HashMap<String, Patient> patients = new HashMap<>();
	HashMap<Integer, Doctor> doctors = new HashMap<>();
	HashMap<String, Integer> assignments = new HashMap<>();

	/**
	 * Add a new clinic patient.
	 * 
	 * @param first
	 *            first name of the patient
	 * @param last
	 *            last name of the patient
	 * @param ssn
	 *            SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {

		patients.put(ssn, new Patient(first, last, ssn));
	}

	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn
	 *            SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient
	 *             in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		Patient p = patients.get(ssn);

		if (p == null)
			throw new NoSuchPatient();

		return p.toString();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first
	 *            first name of the doctor
	 * @param last
	 *            last name of the doctor
	 * @param ssn
	 *            SSN number of the doctor
	 * @param docID
	 *            unique ID of the doctor
	 * @param specialization
	 *            doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {

		doctors.put(docID, new Doctor(first, last, ssn, docID, specialization));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID
	 *            ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor
	 *             in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		Doctor d = doctors.get(docID);

		if (d == null)
			throw new NoSuchDoctor();

		return d.toString();
	}

	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn
	 *            SSN of the patient
	 * @param docID
	 *            ID of the doctor
	 * @throws NoSuchPatient
	 *             in case of not patient with matching SSN
	 * @throws NoSuchDoctor
	 *             in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {

		if (patients.get(ssn) == null)
			throw new NoSuchPatient();

		if (doctors.get(docID) == null)
			throw new NoSuchDoctor();

		assignments.put(ssn, docID);
		doctors.get(docID).addPatient(ssn, patients.get(ssn));
	}

	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn
	 *            SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient
	 *             in case of not patient with matching SSN
	 * @throws NoSuchDoctor
	 *             in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {

		if (patients.get(ssn) == null)
			throw new NoSuchPatient();

		Integer docID = assignments.get(ssn);
		if (docID == null)
			throw new NoSuchDoctor();

		return docID;
	}

	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id
	 *            ID of the doctor
	 * @return collection of patient SSN
	 * @throws NoSuchDoctor
	 *             in case the {@code id} does not match any doctor
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		Doctor d = doctors.get(id);

		if (d == null)
			throw new NoSuchDoctor();

		return d.getPatients();
	}

	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about either a
	 * patient or a doctor.
	 * </p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by
	 * first name, last name, and SSN. Rows containing doctor's info start with
	 * letter {@code "M"}, followed by badge ID, first name, last name, SSN, and
	 * specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.
	 * </p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be
	 * able to ignore the row and skip to the next one.<br>
	 * 
	 * 
	 * @param path
	 *            the path of the required file
	 * @throws IOException
	 *             in case of IO error
	 */
	public void loadData(Reader reader) throws IOException {
		StringBuffer line; // current line
		boolean ancora = true; // new things to read

		while (ancora) {
			line = new StringBuffer();

			int c; // next char
			while ((c = reader.read()) != -1 && c != '\n')
				line.append((char) c);

			ancora = c != -1;

			Pattern pattern;
			Matcher matcher;

			switch (Character.toUpperCase(line.charAt(0))) {
			case 'P': // patient
				pattern = Pattern.compile("^\\w\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*$");
				matcher = pattern.matcher(line);

				if (matcher.find()) {
					String firstName = matcher.group(1);
					String lastName = matcher.group(2);
					String codFisc = matcher.group(3);

					addPatient(firstName, lastName, codFisc);
				}

				break;

			case 'M': // doctor
				pattern = Pattern.compile(
						"^\\w\\s*;\\s*(\\d+)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*;\\s*(\\w+)\\s*$");
				matcher = pattern.matcher(line);

				if (matcher.find()) {
					int ID = Integer.parseInt(matcher.group(1));
					String firstName = matcher.group(2);
					String lastName = matcher.group(3);
					String codFisc = matcher.group(4);
					String spec = matcher.group(5);

					addDoctor(firstName, lastName, codFisc, ID, spec);
				}

				break;
			}
		}
	}

	/**
	 * Retrieves the collection of doctors that have no patient at all, sorted in
	 * alphabetic order.
	 * 
	 * @return the collection of doctors with no patient sorted in alphabetic order
	 *         (last name and then first name)
	 */
	public Collection<Integer> idleDoctors() {
		return doctors.entrySet().stream().filter(e -> e.getValue().getPatients().isEmpty())
				.sorted((e1, e2) -> e1.getValue().getFirstName().compareTo(e2.getValue().getFirstName()))
				.sorted((e1, e2) -> e1.getValue().getLastName().compareTo(e2.getValue().getLastName()))
				.map(e -> e.getKey()).collect(Collectors.toList());
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than
	 * the average.
	 * 
	 * @return the collection of doctors
	 */
	public Collection<Integer> busyDoctors() {
		double average = doctors.entrySet().stream().mapToInt(e -> e.getValue().getPatients().size()).average()
				.getAsDouble();

		return doctors.entrySet().stream().filter(e -> e.getValue().getPatients().size() > average).map(e -> e.getKey())
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned
	 * patients.
	 * <p>
	 * The method returns list of strings formatted as
	 * "{@code ### : ID SURNAME NAME}" where {@code ###} represent the number of
	 * patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients
	 *         count
	 */
	public Collection<String> doctorsByNumPatients() {
		return doctors.entrySet().stream().map(e -> e.getValue()).filter(d -> !d.getPatients().isEmpty())
				.sorted((d1, d2) -> d2.getPatients().size() - d1.getPatients().size())
				.map(d -> String.format("%3d", d.getPatients().size()) + " : " + d.getID() + " " + d.getLastName() + " "
						+ d.getFirstName())
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves the number of patients per (their doctor's) specialization
	 * <p>
	 * The information is a collections of strings structured as
	 * {@code ### - SPECIALITY} where {@code SPECIALITY} is the name of the
	 * speciality and {@code ###} is the number of patients cured by doctors with
	 * such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic
	 * specialization.
	 * 
	 * @return the collection of strings with specialization and patient count
	 *         information.
	 */
	public Collection<String> countPatientsPerSpecialization() {

		return doctors.entrySet().stream().map(e -> e.getValue())
				.collect(Collectors.groupingBy(Doctor::getSpecialization,
						Collectors.summingInt(d -> d.getPatients().size())))
				.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
				.sorted((e1, e2) -> e1.getValue().intValue() - e2.getValue().intValue())
				.filter(e -> e.getValue().intValue() != 0)
				.map(e -> String.format("%3d", e.getValue()) + " - " + e.getKey()).collect(Collectors.toList());
	}

}
