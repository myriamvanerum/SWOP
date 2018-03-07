package controller;

import model.Focusable;

/**
 * ObjectFocusListener implements FocusListener.
 * 
 * @author groep 03
 *
 */
public class ObjectFocusListener implements FocusListener {
    private Focusable focusedObject;

    /**
     * returns the object that is focused.
     * @return Focusable: the focused object.
     */
    public Focusable getFocusedObject(){
        return focusedObject;
    }

    /**
     * The focusable gets focused.
     * @throws IllegalArgumentException
	 * 		Illegal object
     */
    @Override
    public void focusGained(Focusable object) {
    	if (object == null)
			throw new IllegalArgumentException();
        object.focus();
        focusedObject = object;
    }

    /**
     * The focusable gets unfocused.
     */
    @Override
    public void focusLost() {
        focusedObject.unfocus();
        focusedObject = null;
    }

}
