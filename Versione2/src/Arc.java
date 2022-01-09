
public class Arc extends NetObject{

	Place place;
    Transition transition;
    Direction direction;
    
	enum Direction{
		PLACE_TO_TRANSITION,
		TRANSITION_TO_PLACE;
	}

	private Arc(String name, Direction d, Place p, Transition t) {
		super(name);
		this.direction = d;
		this.place = p;
		this.transition = t;
	}
	
	public Arc(String name, Place p, Transition t) {
		this(name, Direction.PLACE_TO_TRANSITION, p, t);
        t.addIncoming(this);
	}
	
	protected Arc(String name, Transition t, Place p) {
        this(name, Direction.TRANSITION_TO_PLACE, p, t);
        t.addOutgoing(this);
    }
	
	public String GetDirection() {
		return direction.toString();
	}
	
}
