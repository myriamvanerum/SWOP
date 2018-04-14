package model;

/**
 * An Actor class. An Actor is a kind of Party
 * @author groep 03
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

    /**
     * Copy Constructor
     * @param party
     */
	public Actor(Party party) {
		super(party);
	}
}
