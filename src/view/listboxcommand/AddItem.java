package view.listboxcommand;

import java.util.ArrayList;

import view.controls.ListBox;
import view.controls.TextBox;

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

	@Override
	public void action() {
		ArrayList<String> items = listBox.getItems();
		items.add(getValue());
	}
}
