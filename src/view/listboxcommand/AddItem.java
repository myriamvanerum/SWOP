package view.listboxcommand;

import java.util.ArrayList;

import view.controls.ListBox;
import view.controls.TextBox;

/**
 * AddItem class.
 * Used to add an item to the listbox
 * 
 * @author groep 03
 *
 */
public class AddItem implements ListBoxOperator {
	private ListBox listBox;
	private TextBox textBox;

	public AddItem(ListBox listbox, TextBox textbox) {
		this.listBox = listbox;
		this.textBox = textbox;
	}
	
	public TextBox getTextbox() {
		return textBox;
	}
	
	public String getValue() {
		return getTextbox().getViewLabel().getOutput();
	}

	/**
	 * Add a new item to the listbox 
	 */
	@Override
	public void action() {
		ArrayList<String> items = listBox.getItems();
		items.add(getValue());
	}
}
