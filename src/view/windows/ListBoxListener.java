package view.windows;

import java.util.ArrayList;

/**
 * ListBoxListener Class
 * Connects a listbox and the dialogbox that has a listbox
 * @author groep 03
 *
 */
public interface ListBoxListener {
	/**
	 * Update the arguments of the listbox
	 * @param arguments
	 * 			ArrayList of all the listbox items
	 */
	void updateArguments(ArrayList<String> arguments);
	
	/**
	 * Check the availability of all listbox buttons
	 * @param index
	 * 			The current index of the listbox
	 * @param size
	 * 			The size of the listbox items
	 */
	void availabilityButtons(int index, int size);
}
