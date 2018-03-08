package controller;


import model.Focusable;

/**
 * The FocusListener handles the focusing of components on the screen, 
 * i.e. when they are selected by the user
 * 
 * @author groep 03
 *
 */
public interface FocusListener {

	/**
	 * The focusable gets focused.
	 * @param object
	 * 		The focusable to focus
	 */
    void focusGained(Focusable object);

    /**
     * The focusable gets unfocused.
     */
    void focusLost();
}
