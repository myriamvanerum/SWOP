package domain.party;

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
     * 		The Party to copy
     */
	public Actor(Party party) {
		super(party);
	}
	
	/**
     * Change the type of a party, this actor becomes an object
     * @return The new party
     */
	@Override
	public Party changeType() {
		return new Object(this);
	}
}
