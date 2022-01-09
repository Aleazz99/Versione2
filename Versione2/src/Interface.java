import java.io.IOException;
import java.util.Scanner;

public class Interface {
	private static final String CORNICE = "-----------------------";
	private static final String AGGIUNGI_RETE = "Aggiungi rete\n";
	private static final String VISUALIZZA_RETE = "Visualizza rete\n";
	private static final String ESCI = "Esci\n";
	private static final String[] VOCI_MENU1 = {AGGIUNGI_RETE, VISUALIZZA_RETE, ESCI};
	private static final String SCEGLI = "Fai la tua scelta";
	
	private static final String AGGIUNGI_POSTO = "Aggiungi posto\n";
	private static final String AGGIUNGI_TRANSIZIONE = "Aggiungi transizione\n";
	private static final String AGGIUNGI_ARCO = "Aggiungi arco\n";
	private static final String TORNA_INDIETRO = "Torna indietro\n";
	private static final String[] VOCI_MENU2 = {AGGIUNGI_POSTO, AGGIUNGI_TRANSIZIONE, AGGIUNGI_ARCO, TORNA_INDIETRO};
	
	private static final String SCEGLI_DIREZIONE = "Scegli la direzione dell'arco";
	private static final String PXT = "Posto -> Transizione";
	private static final String TXP = "Transizione -> Posto";
	private static final String[] VOCI_DIREZIONE = {PXT, TXP};

	private static final String SCEGLI_NOME = "Scegli un nome";
	private static final String MESS_GIA_ESISTENTE = "Attenzione! elemento già presente\n";
	private static final String INSERISCI_POSTO = "Inserisci il nome del posto";	
	private static final String INSERISCI_TRANSIZIONE = "Inserisci il nome della transizione";	
	private static final String INSERISCI_ESISTENTE = "Attenzione! inserire un elemento esistente\n";

	private static final String RICHIESTA_SALVATAGGIO= "Vuoi salvare questa rete? (si/no)->";
	private static final String STATO_SALVATAGGIO= "\nSTATO RETE E SALVATAGGIO:";
	private static final String SALVATAGGIO_RETE= "\nRETE SALVATA CORRETTAMENTE";

	private static final String REQUISITI_NON_SODDISFATTI = "La rete verrà rimossa in quanto non soddisfa i requisiti necessari";
	private static final String REQUISITI_SODDISFATTI = "\nLa rete è sintatticamente corretta";
	
	private static final String MESS_VISUALIZZA_RETE = "Scegli quale visualizzare tra le seguenti reti:";
	private static final String RETE_NON_TROVATA = "rete non trovata";
	private static final String RETE_NON_SALVATA = "\nLa rete non verrà salvata";
	
	private static final String MESS_ALMENO_UNO = "Attenzione! Devi prima inserire almeno una transizione e un posto";
	private static final String MESS_USCITA = "Vuoi uscire lo stesso? (si/no)";
	
	private static final String MESS_RIPETIZIONE = "Attenzione! la coppia è già presente";
	private static final String MESS_CHIUSURA = "Chiusura programma in corso...";
	
	static Scanner in = new Scanner(System.in);
	
	Logic logic = new Logic();
	
	public void start() throws IOException 
	{
		logic.CaricaReti();
		int scelta = 0;
		 do { 
			 System.out.println(CORNICE);
			 for(int i = 0; i < VOCI_MENU1.length; i++) {
				 System.out.println( (i+1) + "\t" + VOCI_MENU1[i]);
			 }
			 System.out.println(SCEGLI);
			 System.out.println(CORNICE);
			 
			 scelta = in.nextInt();
			 switch(scelta) {
			 case 1:
				 AggiungiRete();
				 break;
			 case 2:
				 VisualizzaRete();
				 break;
			 case 3:
				 System.out.println(MESS_CHIUSURA);
				 break;
		 }
		 }while(scelta != 3);
	}
	
	public void AggiungiRete() throws IOException 
	{
		int scelta2 = 0;
		boolean exit;
		String nomeRete = null;
		do {
			exit = true;
			System.out.println(SCEGLI_NOME);
			nomeRete = in.next(); 
			if(logic.ControlloNomeRete(nomeRete)) 
	    		exit = true;
	    	else {
	    		System.out.println(MESS_GIA_ESISTENTE);
	    		exit = false;
	    		}
	    		
		}while(exit!=true);
		
		Net n = new Net(nomeRete);
		logic.AggiungiRete(n);
		
		do {
			System.out.println(CORNICE);
			 for(int i = 0; i < VOCI_MENU2.length; i++) {
				 System.out.println( (i+1) + "\t" + VOCI_MENU2[i]);
			 }
			 System.out.println(SCEGLI);
			 System.out.println(CORNICE);
			
			scelta2 = in.nextInt();
			
			switch(scelta2) {
			case 1:
				System.out.println(SCEGLI_NOME);
				String nomePosto = in.next();
				
				if(!(logic.AggiungiPosto(n, nomePosto)))
					System.out.println(MESS_GIA_ESISTENTE); 
			    
				break;
				
			case 2:
				System.out.println(SCEGLI_NOME);
				String nomeTransizione = in.next();
				
				if(!(logic.AggiungiTransizione(n, nomeTransizione)))
					System.out.println(MESS_GIA_ESISTENTE);
				
				break;
				
			case 3:
				if(!logic.ControllaAlmenoUno(n)) {
					System.out.println(MESS_ALMENO_UNO);
					break;
				}else {
					Place posto = null;
					Transition transizione = null;
					Arc a = null;
					int direzione = 0;
					
					System.out.println(SCEGLI_NOME);
					String nomeA = in.next();
					
					if(logic.ControlloArco(n, nomeA)) {
						do {
							System.out.println(INSERISCI_POSTO);
							String nomeP = in.next();
							posto = n.creaPostoByName(nomeP);
							
							if(posto == null) 
								System.out.println(INSERISCI_ESISTENTE);
						}while(posto == null);
						
						do {
							System.out.println(INSERISCI_TRANSIZIONE);
							String nomeT = in.next();
							transizione = n.creaTransizioneByName(nomeT);
							
							if(transizione == null)
								System.out.println(INSERISCI_ESISTENTE);
						}while(transizione == null);
						
						do {
							System.out.println(SCEGLI_DIREZIONE);
							for(int i = 0; i < VOCI_DIREZIONE.length; i++) {
								 System.out.println( (i+1) + "\t" + VOCI_DIREZIONE[i]);
							 }
							direzione = in.nextInt();
						}while((direzione != 1) && (direzione != 2));
						
						//direzione=1 -> posto-transione
						//direzione=2 -> transizione-posto
						
						if(direzione == 1) {
							//controllo se è una ripetizione
							if(!logic.AggiungiArco(n, nomeA, posto, transizione))
								System.out.println(MESS_RIPETIZIONE);
							
						}
						else if(direzione == 2) {
							//controllo se è una ripetizione
							if(!logic.AggiungiArco(n, nomeA, transizione, posto ))
								System.out.println(MESS_RIPETIZIONE);
						}
				
					}
					else
						System.out.println(MESS_GIA_ESISTENTE);
				}
				break;
				
			case 4: 
				
				if(!logic.ControllaAlmenoUno(n)) {
					System.out.println(MESS_USCITA);
					String resp = in.next();
					if(resp.equals("si")) {
						logic.RimuoviRete(n);
						scelta2 = 4;
					}else if(resp.equals("no")) {
						scelta2 = 0;
					}
				}else {
					SalvataggioRete(n);
				}
				break;		
			}
		}while(scelta2 != 4);
	}
	
	private void VisualizzaRete() 
	{	
		System.out.println(MESS_VISUALIZZA_RETE);
		for(Net n: logic.nets)
			System.out.print(n.getName() + " ");
		
		System.out.print("\n");
		String rete = in.next();
		Net net = logic.CercaReteByName(rete);
		if(net == null)
			System.out.println(RETE_NON_TROVATA);
		else {
			System.out.println(net.toString());
		}
		System.out.print("\n");
	}
	
	
	public void SalvataggioRete(Net daSalvare) throws IOException {
		
		System.out.println(STATO_SALVATAGGIO);
		
		if(logic.ControlliRete(daSalvare)) {
			System.out.println(REQUISITI_SODDISFATTI);
			System.out.println(RICHIESTA_SALVATAGGIO);
			String risposta = in.next();
	
			if(risposta.equals("si")) {
				logic.Scrittura(daSalvare);
				System.out.println(SALVATAGGIO_RETE);
			}
			else
				System.out.println(RETE_NON_SALVATA);
		}
		else 
		{
			System.out.println(REQUISITI_NON_SODDISFATTI);
			logic.RimuoviRete(daSalvare);
		}
		System.out.println();
	}
	
}
