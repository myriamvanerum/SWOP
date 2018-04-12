package model;

/**
 * An Actor class. An actor is a kind of party
 * 
 * @author groep 03
 * 
 *
 */
public class Actor extends Party {
	/**
	 * Actor constructor
	 * @param label
	 * 		The actor's label
	 */
    public Actor(String label) {
        super(label);
    }

	public Actor(Party party) {
		super(party);
	}
}
