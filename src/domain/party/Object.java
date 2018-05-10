package model.party;

/**
 * An Object class, a type of Party
 * @author groep 03
 */
public class Object extends Party {

	/**
	 * Object constructor
	 * @param label
	 * 		The object's label
	 */
    public Object(String label) {
		super(label);
	}

    /**
     * Copy Constructor
     * @param party
     * 		The Party to copy
     */
	public Object(Party party) {
		super(party);
	}
	
	public Party changeType() {
		return new Actor(this);
	}
}
