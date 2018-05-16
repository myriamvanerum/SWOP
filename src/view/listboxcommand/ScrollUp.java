package view.listboxcommand;

import view.formelements.ListBox;

public class ScrollUp implements ListBoxOperator {
	ListBox listBox;

	public ScrollUp(ListBox listbox) {
		this.listBox = listbox;
	}

	@Override
	public void action() {		
		if (listBox.getSelectedItem() != 0) 
			listBox.setSelectedItem(listBox.getSelectedItem()-1);
	}
}
