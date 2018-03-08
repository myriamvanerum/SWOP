package model;

/**
 * An interface Focusable that determines what properties a focusable has.
 * 
 * @author groep 03
 *
 */
public interface Focusable {

	/**
     * This method determines if the object is focused
	 * @return true is focused, false if not focused
	 */
    Boolean focused();

    /**
     * This method focuses an object
     */
    void focus();

    /**
     * This method unfocuses an object
     */
    void unfocus();
}
