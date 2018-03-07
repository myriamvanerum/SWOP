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
	 * @param x
	 * 		The x coordinate of the actor
	 * @param y
	 * 		The y coordinate of the actor
	 * @param label
	 * 		The actor's label
	 */
    public Actor(int x, int y, Label label) {
        super(x, y, label);
    }
}
