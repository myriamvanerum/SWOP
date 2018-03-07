package model;

/*
 * An Object class
 * 
 * @author SWOP groep 03
 */
public class Object extends Party {

	/**
	 * Object constructor
	 * @param x
	 * 		The x coordinate of the object
	 * @param y
	 * 		The y coordinate of the object
	 * @param label
	 * 		The object's label
	 */
    public Object(int x, int y, Label label) {
		//label.setX(x + ((80 / 2) - label.getText().length() * 2));
		//label.setY(y+ height / 2);
		super(x, y, label);
	}
}
