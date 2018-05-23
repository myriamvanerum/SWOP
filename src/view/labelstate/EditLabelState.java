package view.labelstate;

import java.awt.Color;

import domain.Component;
import domain.SyntaxChecker;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.windows.SubWindow;

/**
 * EditLabelState class.
 * Default state that processes all key input, when a label is being edited
 * @author groep 03 
 */
public class EditLabelState {
	SyntaxChecker syntaxChecker;
	SubWindow subwindow;
	ViewLabel viewLabel;
		
	/**
	 * EditLabelState Constructor
	 * @param subwindow
	 * 			The subwindow in which a label is being edited
	 * @param viewLabel
	 * 			The viewlabel that is being edited
	 */
	public EditLabelState(SubWindow subwindow, ViewLabel viewLabel) {
		this.syntaxChecker = new SyntaxChecker();
		this.subwindow = subwindow;
		this.viewLabel = viewLabel;
	}
	
	/* Getters & Setters */
	
	public ViewLabel getViewLabel() {
		return viewLabel;
	}

	public void setViewLabel(ViewLabel viewLabel) {
		this.viewLabel = viewLabel;
	}
	
	public SubWindow getSubWindow() {
		return subwindow;
	}

	public ViewComponent getCurrentViewComponent() {
		return subwindow.getSelectedComponent();
	}
	
	/**
	 * Remove the last character of the label
	 */
	public void removeCharacter() {
		String label = getViewLabel().getOutput();
		
		if (label.length() <= 1) return;
		getViewLabel().setOutput(label.substring(0, label.length() - 2) + "|");		
	}
	
	/**
	 * Add a character to the label
	 * @param keyCode
	 * 			KeyCode of the key input
	 * @param keyChar
	 * 			Character input
	 */
	public void addCharacter(int keyCode, char keyChar) {
		getViewLabel().editLabel(keyChar);
		getViewLabel().setColor(Color.GREEN);
	}
	
	/**
	 * Set a label with the given input
	 * @param keyChar
	 * 			Character input
	 */
	public void editLabel(char keyChar) {
		String label = getViewLabel().getOutput();
		if (label.length() > 0)
			getViewLabel().setOutput(label.substring(0, label.length() - 1) + keyChar + "|");
		else 
			getViewLabel().setOutput(keyChar + "|");
	}
	
	/**
	 * Confirm the current input
	 */
	public void confirmLabel() {
		enterLabel();
	}
	
	/**
	 * Set the label of a component
	 */
	public void enterLabel() {
		Component component = getCurrentViewComponent().getComponent();
		String label = getViewLabel().getOutput();		
		getViewLabel().setColor(Color.BLACK);		
		label = label.substring(0, label.length() - 1);
		getSubWindow().editLabel(component, label);
		getViewLabel().setOutput(label);	
	}

	/**
	 * Add an argument
	 */
	public void addArgument() {}
}
