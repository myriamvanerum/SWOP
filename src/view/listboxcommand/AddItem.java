package view.listboxcommand;

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
		return getTextbox().getValue();
	}

	/**
	 * Add a new item to the listbox 
	 */
	@Override
	public void action() {
		listBox.add(getValue());
	}
}
