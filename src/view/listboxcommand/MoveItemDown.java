package view.listboxcommand;

import view.controls.ListBox;

/**
 * MoveItemDown class.
 * Used to move the selected item one place down in the listbox
 * 
 * @author groep 03
 *
 */
public class MoveItemDown implements ListBoxOperator {
	ListBox listBox;

	public MoveItemDown(ListBox listbox) {
		this.listBox = listbox;
	}

	/**
	 * Move the current selected item one place down in the listbox
	 */
	@Override
	public void action() {
		listBox.moveDown();
	}

}
