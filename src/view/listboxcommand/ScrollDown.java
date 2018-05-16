package view.listboxcommand;

import java.util.ArrayList;

import view.formelements.ListBox;

public class ScrollDown implements ListBoxOperator {
	ListBox listBox;

	public ScrollDown(ListBox listbox) {
		this.listBox = listbox;
	}

	@Override
	public void action() {
		ArrayList<String> items = listBox.getItems();
		int index = listBox.getSelectedItem();
		
		if (index != items.size()-1) 
			listBox.setSelectedItem(index+1);
	}
}
