package view.listboxcommand;

import java.util.ArrayList;

import view.controls.ListBox;

/**
 * ScrollDown class.
 * Used to select the item beneath the selected item in the listbox
 * 
 * @author groep 03
 *
 */
public class ScrollDown implements ListBoxOperator {
	ListBox listBox;

	public ScrollDown(ListBox listbox) {
		this.listBox = listbox;
	}

	/**
	 * Selected the item beneath the current selected item
	 */
	@Override
	public void action() {
		listBox.scrollDown();
	}
}
