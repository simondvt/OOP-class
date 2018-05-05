package university;

public class Studente {
	
	// costanti
	private static final int MAX_CORSI = 25;
	
	// attributi
	private int matricola;
	private String nome, cognome;
	
	private int currentCorso;
	private int[] corsi = {-1};
	
	public Studente(int matricola, String nome, String cognome) {
		this.matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
		
		currentCorso = 0;
		corsi = new int[MAX_CORSI];
	}
	
	public String toString() {
		return matricola + " " + nome + " " + cognome;
	}
	
	public int getMatricola() {
		return matricola;
	}
	
	public void addCorso(int code) {
		if (currentCorso >= MAX_CORSI) {
			System.out.println("Errore! Numero massimo di insegnamenti per studente raggiunto!");
			return;
		}
		
		corsi[currentCorso++] = code;
	}
	
	public String infoCorsi(Corso[] totCorsi) {
		StringBuffer sb = new StringBuffer();
		
		for (int code : corsi) {
			if (code != -1) {
				for (Corso c : totCorsi) {
					if (c == null) break;
					
					if (c.getCodice() == code) {
						sb.append(c.toString() + "\n");
						break;
					}
				}
			}
		}
		
		return sb.toString();
	}
}
