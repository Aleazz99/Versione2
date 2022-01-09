import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Net extends NetObject{
	List<Place> places = new ArrayList<Place>();
	List<Transition> transitions = new ArrayList<Transition>();
	List <Arc> arcs = new ArrayList<Arc>();
	HashMap<Place, Transition> pxt = new HashMap();
	HashMap<Transition, Place> txp = new HashMap();
	
	public Net(String name) {
		super(name);
	}
	
    public Transition transition(String name) {
        Transition t = new Transition(name);
        transitions.add(t);
        return t;
    }
    
    public Place place(String name) {
    	
    	for(Place posto : places) {
    		if(name.equals(posto.getName()))
    			return posto;
    	}
    	
		Place p = new Place(name);
		places.add(p);
	
        return p;
    }
    
    public Arc arc(String name, Place p, Transition t) {
        Arc arc = new Arc(name, p, t);
        arcs.add(arc);
        
        pxt.put(p, t);
        return arc;
    }
    
    public Arc arc(String name, Transition t, Place p) {
        Arc arc = new Arc(name, t, p);
        arcs.add(arc);
        
        txp.put(t, p);
        return arc;
    }
    
    public Place creaPostoByName(String name) {
    	for(Place p: places) {
    		if((p.getName()).equals(name))
    			return p;
    	}
    	return null;
    }
    
    public Transition creaTransizioneByName(String name) {
    	for(Transition t: transitions) {
    		if((t.getName()).equals(name))
    			return t;
    	}
    	return null;
    }
    
    public boolean cercaTransizioneByName(String name) {
    	for(Transition t: transitions) {
    		if((t.getName()).equals(name))
    			return true;
    	}
    	return false;
    }
    
    public boolean cercaPostoByName(String name) {
    	for(Place p: places) {
    		if((p.getName()).equals(name))
    			return true;
    	}
    	return false;
    }
    
    public boolean cercaArcoByName(String name) {
    	for(Arc a: arcs)
    		if(a.getName().equals(name))
    			return true;
    	
		return false;
    }
    
    //controllo:tutti i posti e tutte le transizioni devono essere connesse
    public boolean controllaConnessione() {
    	int i;
    	int j;
    	for(Transition t: transitions) {
    		i = 0;
    		for(Place p: places) {
    			if(pxt.get(p) == t)
    				i++;
    		}
    		
    		if(i == 0) return false;
    	}
    	
    	for(Place p: places) {
    		j = 0;
    		for(Transition t: transitions) {
    			if(txp.get(t) == p)
    				j++;
    		}
    		
    		if(j == 0) return false;
    	}
    	
    	return true;
    }
    
    public boolean controllaRipetizioni(Place p, Transition t) {
    	if(pxt.get(p) == t)
    		return true;
    	return false;
    }
    
    public boolean controllaRipetizioni(Transition t, Place p) {
    	if(txp.get(t) == p)
    		return true;
    	return false;
    }
    
    public HashMap<Transition, Place> getTXP(){
    	return txp;
    }
    
    public HashMap<Place, Transition> getPXT(){
    	return pxt;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public String toString() {
    	StringBuilder sb = new StringBuilder("");
    	sb.append("Nome rete: ").append(getName());
    
		for(Arc ar: arcs) {	
			sb.append("\nArco ").append(ar.getName());
			
			if(ar.GetDirection().equals("PLACE_TO_TRANSITION")) {
				sb.append("(p,t): ").append(ar.place.getName()).append(" -> ").append(ar.transition.getName());
			}else{
				sb.append("(t,p): ").append(ar.transition.getName()).append(" -> ").append(ar.place.getName());
			}
		}
		
		return sb.toString();
    }
}
