package view;
/**
 * An interface Selectable that determines what properties a Selectable has.
 * 
 * @author groep 03
 *
 */
public interface Selectable {
	/**
     * This method determines if the object is selected
	 * @return true is selected, false if not selected
	 */
	public boolean selected();
	
	/**
     * This method selects an object
     */
	public void select();
	
	/**
     * This method unselects an object
     */
	public void unselect();
}