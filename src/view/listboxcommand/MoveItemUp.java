package view.listboxcommand;

import java.util.ArrayList;

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
		ArrayList<String> items = listBox.getItems();
		int index = listBox.getSelectedItem();
		
		if (index != 0) {
			String temp = items.get(index-1);
			items.set(index-1, items.get(index));
			items.set(index, temp);
			listBox.setSelectedItem(index-1);
		}

		listBox.getListener().moveItemDown(listBox.getItems());
		listBox.getListener().availabilityButtons(listBox.getSelectedItem(), listBox.getItems().size());
	}
}
