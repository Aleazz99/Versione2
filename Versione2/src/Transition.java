import java.util.ArrayList;
import java.util.List;

public class Transition extends NetObject {
	private List<Arc> incoming = new ArrayList<Arc>();
    private List<Arc> outgoing = new ArrayList<Arc>();
	
	public Transition(String name) {
		super(name);
	}
	
    public void addIncoming(Arc arc) {
        this.incoming.add(arc);
    }
    
    public void addOutgoing(Arc arc) {
        this.outgoing.add(arc);
    }
    
//    public boolean isConnesso() {
//    	if() {
//    		
//    	}
//    }
}
