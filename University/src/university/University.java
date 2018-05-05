package university;

public class University {
	
	// costanti
	private static final int STARTING_MATRICOLA = 10000;
	private static final int MAX_STUDENTI = 1000;
	
	private static final int STARTING_CODICE = 10;
	private static final int MAX_CORSI = 50;


	// attributi
	private String nomeAteneo;
	private String nomeRettore, cognomeRettore;
	
	private int currentMatricola;
	private int currentCodice;
	private Studente[] studenti;
	private Corso[] corsi;
	
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		nomeAteneo = name;
		
		currentMatricola = STARTING_MATRICOLA;
		studenti = new Studente[MAX_STUDENTI];
		currentCodice = STARTING_CODICE;
		corsi = new Corso[MAX_CORSI];
	}
	
	/**
	 * Getter for the name of the university
	 * @return name of university
	 */
	public String getName(){
		return nomeAteneo;
	}
	
	/**
	 * Defines the rector for the university
	 * @param first
	 * @param last
	 */
	public void setRector(String first, String last){
		nomeRettore = first;
		cognomeRettore = last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * @return
	 */
	public String getRector(){
		return nomeRettore + " " + cognomeRettore;
	}
	
	/**
	 * Enroll a student in the university
	 * @param first first name of the student
	 * @param last last name of the student
	 * @return
	 */
	public int enroll(String first, String last) {
		if (currentMatricola - STARTING_MATRICOLA >= MAX_STUDENTI) {
			System.out.println("Errore! Numero massimo di studenti raggiunto!");
			return -1;
		}
		
		studenti[currentMatricola - STARTING_MATRICOLA] = 
				new Studente(currentMatricola, first, last);
		
		currentMatricola++;
		return currentMatricola - 1;
	}
	
	/**
	 * Retrieves the information for a given student
	 * @param id the id of the student
	 * @return information about the student
	 */
	public String student(int id){
		for (Studente s : studenti)
			if (s != null && s.getMatricola() == id)
				return s.toString();
		
		System.out.println("Errore! Studente non trovato!");
		return null;
	}
	
	/**
	 * Activates a new course with the given teacher
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		if (currentCodice - STARTING_CODICE >= MAX_CORSI) {
			System.out.println("Errore! Numero massimo di insegnamenti raggiunto!");
			return -1;
		}
		
		corsi[currentCodice - STARTING_CODICE] = 
				new Corso(currentCodice, title, teacher);
		
		currentCodice++;
		return currentCodice - 1;
	}
	
	/**
	 * Retrieve the information for a given course
	 * @param code unique code of the course
	 * @return information about the course
	 */
	public String course(int code){
		for (Corso c : corsi)
			if (c != null && c.getCodice() == code)
				return c.toString();
		
		System.out.println("Errore! Corso non trovato!");
		return null;
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		for (Studente s : studenti)
			if (s != null && s.getMatricola() == studentID)
				s.addCorso(courseCode);
		
		for (Corso c : corsi)
			if (c != null && c.getCodice() == courseCode)
				c.addStudente(studentID);
	}
	
	/**
	 * Retrieve a list of attendees
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){		
		for (Corso c : corsi)
			if (c != null && c.getCodice() == courseCode) 
				return c.infoStudenti(studenti);
		
		System.out.println("Errore! Corso non trovato!");
		return null;
	}

	/**
	 * Retrieves the study plan for a student
	 * @param studentID id of the student
	 * @return list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		for (Studente s : studenti)
			if (s != null && s.getMatricola() == studentID) 
				return s.infoCorsi(corsi);
		
		System.out.println("Errore! Studente non trovato!");
		return null;
	}
}
