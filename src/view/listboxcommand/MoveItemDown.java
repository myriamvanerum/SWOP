package view.listboxcommand;

import java.util.ArrayList;

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
		ArrayList<String> items = listBox.getItems();
		int index = listBox.getSelectedItem();
		
		if (index != items.size()-1) {
			String temp = items.get(index+1);
			items.set(index+1, items.get(index));
			items.set(index, temp);
			listBox.setSelectedItem(index+1);
		}
	}

}
