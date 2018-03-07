package controller;

import model.Focusable;

public class ObjectFocusListener implements FocusListener {
    private Focusable focusedObject;

    public Focusable getFocusedObject(){
        return focusedObject;
    }

    @Override
    public void focusGained(Focusable object) {
        object.focus();
        focusedObject = object;
    }

    @Override
    public void focusLost() {
        focusedObject.unfocus();
        focusedObject = null;
    }

}
