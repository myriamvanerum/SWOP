package view.listboxcommand;

import java.util.ArrayList;

import view.controls.ListBox;

public class DeleteItem implements ListBoxOperator {
	ListBox listBox;

	public DeleteItem(ListBox listbox) {
		this.listBox = listbox;
	}

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
	}
}
