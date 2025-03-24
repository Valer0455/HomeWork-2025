package diadia;

import java.util.Scanner;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @version base
 */

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""+
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n"+
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
			"Per conoscere le istruzioni usa il comando 'aiuto'.";
	
	static final private String[] elencoComandi = {"vai", "aiuto", "fine"};

	private Partita partita;

	public DiaDia(Partita partita) { // ho cambiato il costruttore
		this.partita = partita;
	}

	public void gioca() {
		String istruzione; 
		Scanner scannerDiLinee;

		System.out.println(MESSAGGIO_BENVENUTO);
		scannerDiLinee = new Scanner(System.in);	
		//System.out.println("🔍 CFU iniziali: " + this.partita.getCfu());
		//System.out.println("🔍 Partita finita? " + this.partita.isFinita());

		do		
			istruzione = scannerDiLinee.nextLine();
		while (this.partita.isFinita() || !processaIstruzione(istruzione));
	}   


	/**
	 * Processa una istruzione 
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		//System.out.println("🔵 Processando: " + istruzione);
		Comando comandoDaEseguire = new Comando(istruzione);
		
		if (comandoDaEseguire.getNome().equals("fine")) {
			this.fine(); 
			return true;
		} else if (comandoDaEseguire.getNome().equals("vai"))
			this.vai(comandoDaEseguire.getParametro());
		else if (comandoDaEseguire.getNome().equals("aiuto"))
			this.aiuto();
		else if(comandoDaEseguire.getNome().equals("null")) {
			System.out.println("Comando sconosciuto");
		}
		else
			System.out.println("Comando sconosciuto");
		if (this.partita.isFinita()) {
			System.out.println("Hai finito i CFU!");
			return true;
		}
		else if(this.partita.vinta()) {
			System.out.println("Hai vinto!");
			return true;
		} else {
			return false;
		}
		
	}   

	// implementazioni dei comandi dell'utente:

	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		for(int i=0; i< elencoComandi.length; i++) 
			System.out.print(elencoComandi[i]+" ");
		System.out.println();
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra 
	 * e ne stampa il nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		
		if(direzione==null)
			System.out.println("Dove vuoi andare ?");
		Stanza prossimaStanza = null;
		prossimaStanza = this.partita.getStanzaCorrente().getStanzaAdiacente(direzione);
		//System.out.println("Oggetto Partita in vai(): " + this.partita);
		if (prossimaStanza == null)
			System.out.println("Direzione inesistente");
		else {
			this.partita.setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getCfu();
			System.out.println("cfu prima di muoversi " + cfu);
			cfu--;
			this.partita.setCfu(cfu);
			System.out.println("cfu dopo che ci siamo mossi " + this.partita.getCfu());
		}
		System.out.println(partita.getStanzaCorrente().getDescrizione());
	}

	/**
	 * Comando "Fine".
	 */
	private void fine() {
		System.out.println("Grazie di aver giocato!");  // si desidera smettere
	}

	public static void main(String[] argc) { // ho cambiato il main
		Partita partita = new Partita();
		DiaDia gioco = new DiaDia(partita);
		gioco.gioca();
	}
}