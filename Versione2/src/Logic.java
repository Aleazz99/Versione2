import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Logic 
{
	private static final String CONNESSIONE_OK  = "La rete %s è correttamente connessa";
	private static final String CONNESSIONE_NOK = "Non tutti i posti/transizioni della rete %s sono connessi\n";
	
	private static final String fileName = "C:\\\\Users\\\\azzin\\\\git\\\\repository\\\\Versione1\\\\file.cvs";
	
	//C:\\Users\\azzin\\git\\repository\\Versione1\\file.cvs
	//C:\\Users\\bolgi\\git\\repository\\Versione1\\file.cvs
	
	//token Bolgiani: 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
	static Scanner in = new Scanner(System.in);
	static List<Net> nets = new ArrayList<Net>();
	
	public boolean ControlloNomeRete(String nomeRete)
	{
		boolean stato = true;
		for(Net n: nets) {
    		if(n.getName().equals(nomeRete)) {
    			stato = false;
    			break;
    		}
    	}
		return stato;
	}
	
	public void AggiungiRete (Net rete)
	{
		nets.add(rete);
	}
	
	public void RimuoviRete (Net rete)
	{
		nets.remove(rete);
	}
	
	public boolean AggiungiPosto(Net n, String nomePosto)
	{
		boolean stato = false;
		if(!(n.cercaPostoByName(nomePosto))) {
	    	Place p = n.place(nomePosto); 
	    	stato = true;
	    }
		return stato;
	}
	
	public boolean AggiungiTransizione(Net n, String nomeTransizione)
	{
		boolean stato = false;
		if(!(n.cercaTransizioneByName(nomeTransizione))) {
	    	Transition t = n.transition(nomeTransizione); 
	    	stato = true;
	    }
		return stato;
	}
	
	public boolean ControlloArco (Net n, String nomeArco)
	{
		boolean stato = true;
		if(n.cercaArcoByName(nomeArco)) {
			stato = false;
		}
		return stato;
	}
	
	public boolean AggiungiArco (Net n, String nomeArco, Place posto, Transition transizione)
	{
		boolean stato = false;
		if(!n.controllaRipetizioni(posto, transizione)) {
			Arc a = n.arc(nomeArco, posto, transizione );
			stato = true;
		}
		return stato;
	}
	
	public boolean AggiungiArco (Net n, String nomeArco,  Transition transizione, Place posto)
	{
		boolean stato = false;
		if(!n.controllaRipetizioni(transizione, posto)) {
			Arc a = n.arc(nomeArco, transizione, posto );
			stato = true;
		}
		return stato;
	}
	
	public boolean ControlliRete (Net rete) throws IOException
	{
		boolean stato = false;
		if(ControllaRete(rete) && ControllaUnicita(rete)) {
			stato = true;
		}
		else 
			nets.remove(rete);
		
		return stato;
	}
	
	//scrittura della nuova rete su file
	public void Scrittura(Net n) throws IOException 
	{
		if(n != null) {
			FileWriter fw = null;
			BufferedWriter bw = null;
			PrintWriter pw = null;
			try {
				fw = new FileWriter(fileName, true);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
				
				pw.printf(n.getName());
				pw.print(",");
				int count = 0;
				for(Arc a: n.arcs) {
					pw.printf(a.getName());
					pw.print(":");
					pw.printf(a.place.getName());
					pw.print(":");
					pw.printf(a.transition.getName());
					pw.print(":");
					pw.printf(a.direction.toString());
					count++;
					if(count != n.arcs.size())
						pw.print(",");
				}
				pw.println();
			
				}finally {
					try {
						pw.close();
						fw.close();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		     
				}	
		}
		
	}
	
	
	//questo metodo carica direttamente tutte le reti scritte nel file della lista delle reti
	public void CaricaReti () throws IOException
	{			         
		BufferedReader reader = null;
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(fileName));
	
				while((line = reader.readLine()) != null ) {
					String[] row = line.split(",");
					
					Net nuovaRete = null;
					Place p = null;
					Transition t = null;
					Arc a = null;
				    nuovaRete = new Net(row[0]);
				    
					for(String o : row) {
						if(!o.equals(row[0])){
							String[] obje = o.split(":");
							p = nuovaRete.place(obje[1]);	
							t = nuovaRete.transition(obje[2]);
							
							if(obje[3].equals("PLACE_TO_TRANSITION"))
								a = nuovaRete.arc(obje[0], p, t);
							else
								a = nuovaRete.arc(obje[0], t, p);
						}
					}
					//rete aggiunta alla lista di reti
					nets.add(nuovaRete);
				}
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			reader.close();
		}
	}
	
	//controllo: ogni rete deve contenere almeno un posto e una transizione
	public boolean ControllaAlmenoUno(Net n) {
		boolean s = false;
		if((n.transitions).isEmpty() || (n.places).isEmpty())
			s = false;
		else 
			s = true;
		
		return s;
	}
	
	//controllo se la rete è correttamente connessa
	private static boolean ControllaRete(Net n) 
	{
		boolean stato=false;
				
			if(n != null) {
				if(n.controllaConnessione()) {
					System.out.printf(CONNESSIONE_OK, n.getName());
					stato = true;
				}else {
					System.out.printf(CONNESSIONE_NOK, n.getName());
					System.out.println();
					stato = false;
				}
			}	
			
		return stato;
	}
		
	//controllo che la rete sia unica
	private static boolean ControllaUnicita(Net daControllare) {
		boolean stato = false;
		
		if(daControllare != null) {
			if(nets.size() == 1)
				stato = true;
			
			if(!(ControllaPXT(daControllare)) && !(ControllaTXP(daControllare))) 
				System.out.print(" ma la rete è già esistente\n");
			else
				stato = true;	
		}
		
		return stato;
	}
	
	//controllo che non esistano già coppie posto-transizione uguali in altre reti12
	private static boolean ControllaPXT(Net net1) {
		boolean stato = true;
		HashMap<Place,Transition> rete1PXT = net1.getPXT();
		
		for(Net net2 : nets) {
			HashMap<Place, Transition> rete2PXT = net2.getPXT();
			
			if(rete1PXT.keySet().equals(rete2PXT.keySet())) {
				if(!(ControllaValoriPXT(rete1PXT, rete2PXT))) {
					stato = false;
					break;
				}
			}
		}
		
		return stato;
	}
	
	//controllo che non esista già coppie transizione-posto uguali in altre reti
	private static boolean ControllaTXP(Net net1) {
		boolean stato = true;
		HashMap<Transition,Place> rete1TXP = net1.getTXP();
		
		for(Net net2 : nets) {
			HashMap<Transition, Place> rete2TXP = net2.getTXP();
			
			if(rete1TXP.keySet().equals(rete2TXP.keySet())) 
				if(!(ControllaValoriTXP(rete1TXP, rete2TXP))) {
					stato = false;
					break;
				}
		}
		
		return stato;
	}
	
	private static boolean ControllaValoriPXT(HashMap<Place,Transition> r1, HashMap<Place,Transition> r2) {
		List<Transition> transizioni1 = new ArrayList<Transition>(r1.values());
		List<Transition> transizioni2 = new ArrayList<Transition>(r2.values());
		return transizioni1.equals(transizioni2);
		 
	}
	
	private static boolean ControllaValoriTXP(HashMap<Transition,Place> r1, HashMap<Transition,Place> r2) {
		List<Place> posti1 = new ArrayList<Place>(r1.values());
		List<Place> posti2 = new ArrayList<Place>(r2.values());
		return posti1.equals(posti2);
	}
	
	public Net CercaReteByName(String name) 
	{
    	for(Net n: nets) {
    		if(n.getName().equals(name))
    			return n;
    	}
    	return null;
    }
	
}
