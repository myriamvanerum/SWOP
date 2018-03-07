package model;

/**
 * An interface Focusable that determines what properties a focusable has.
 * 
 * @author groep 03
 *
 */
public interface Focusable {

    Boolean focused();

    void focus();

    void unfocus();
}
