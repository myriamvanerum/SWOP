package domain;

import view.LabelObserver;

public interface LabelObservable {
	/**
	 * Add an Observer to this Observable
	 * @param o
	 * 		The observer to add
	 */
	public void addObserver(LabelObserver o);
	
	/**
	 * Remove an Observer from this Observable
	 * @param o
	 * 		The observer to remove
	 */
	public void removeObserver(LabelObserver o);
	/**
	 * Notify all Observers a label has been edited
	 * @param component
	 *		The component that has had it's label edited
	 */
	public void notifyEditLabel(Component component);
}
