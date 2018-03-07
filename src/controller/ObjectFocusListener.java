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
     */
    @Override
    public void focusGained(Focusable object) {
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
