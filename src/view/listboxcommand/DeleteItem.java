package view.listboxcommand;

import view.controls.ListBox;

/**
 * DeleteItem class.
 * Used to delete the selected item in the listbox
 * 
 * @author groep 03
 *
 */
public class DeleteItem implements ListBoxOperator {
	ListBox listBox;

	public DeleteItem(ListBox listbox) {
		this.listBox = listbox;
	}

	/**
	 * Delete the current selected item in the listbox
	 */
	@Override
	public void action() {
		listBox.remove();
	}
}
