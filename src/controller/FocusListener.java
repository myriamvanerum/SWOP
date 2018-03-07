package controller;


import model.Focusable;

public interface FocusListener {

    void focusGained(Focusable object);

    void focusLost();
}
