package view;

import domain.Component;

public interface LabelObserver {
	/**
	 * Method to be called when a Component's label has been edited
	 * @param component
	 *		The component that has had it's label edited
	 */
	public void onEditLabel(Component component);
}
