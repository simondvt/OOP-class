package university;

public class Corso {
	
	// costanti
	private static final int MAX_STUDENTI = 100;
	
	// attributi
	private int codice;
	private String titolo;
	private String docente;
	
	private int currentStudente;
	private int[] studenti = {-1};
	
	public Corso(int codice, String titolo, String docente) {
		this.codice = codice;
		this.titolo = titolo;
		this.docente = docente;
		
		currentStudente = 0;
		studenti = new int[MAX_STUDENTI];
	}
	
	public String toString() {
		return codice + " " + titolo + " " + docente;
	}
	
	public int getCodice() {
		return codice;
	}
	
	public void addStudente(int matricola) {
		if (currentStudente >= MAX_STUDENTI) {
			System.out.println("Errore! Numero massimo di studenti per insegnamento raggiunto!");
			return;
		}
		
		studenti[currentStudente++] = matricola;
	}
	
	public String infoStudenti(Studente[] totStudenti) {
		StringBuffer sb = new StringBuffer();
		
		for (int matricola : studenti) {
			if (matricola != -1) {
				for (Studente s : totStudenti) {
					if (s == null) break;
					
					if (s.getMatricola() == matricola) {
						sb.append(s.toString() + "\n");
					}
				}
			}
		}
		
		return sb.toString();
	}
}
