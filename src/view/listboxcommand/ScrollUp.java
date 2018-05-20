package view.listboxcommand;

import view.controls.ListBox;

/**
 * ScrollUp class.
 * Used to select the item above the selected item in the listbox
 * 
 * @author groep 03
 *
 */
public class ScrollUp implements ListBoxOperator {
	ListBox listBox;

	public ScrollUp(ListBox listbox) {
		this.listBox = listbox;
	}

	/**
	 * Selected the item above the current selected item
	 */
	@Override
	public void action() {		
		listBox.scrollUp();
	}
}
