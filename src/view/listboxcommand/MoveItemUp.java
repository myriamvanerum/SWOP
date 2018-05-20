package view.listboxcommand;

import view.controls.ListBox;

/**
 * MoveItemUp class.
 * Used to move the selected item one place up in the listbox
 * 
 * @author groep 03
 *
 */
public class MoveItemUp implements ListBoxOperator {
	ListBox listBox;
	
	public MoveItemUp(ListBox listbox) {
		this.listBox = listbox;
	}

	/**
	 * Move the current selected item one place up in the listbox
	 */
	@Override
	public void action() {
		listBox.moveUp();
	}
}
