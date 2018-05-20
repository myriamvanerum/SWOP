package view.listboxcommand;

import java.util.ArrayList;

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
		ArrayList<String> items = listBox.getItems();
		int index = listBox.getSelectedItem();
				
		if (items.size() > 0) {			
			if (items.size() > 1)
				listBox.setSelectedItem(0);
			else listBox.setSelectedItem(-1);	
			
			items.remove(index);		
		}
		
		listBox.getListener().moveItemDown(listBox.getItems());
		listBox.getListener().availabilityButtons(listBox.getSelectedItem(), listBox.getItems().size());
	}
}
