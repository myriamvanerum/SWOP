package view.labelstate;

import java.awt.Color;

import domain.Component;
import domain.SyntaxChecker;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.windows.SubWindow;

public class EditLabelState {
	SyntaxChecker syntaxChecker;
	SubWindow subwindow;
	ViewLabel viewLabel;
		
	public EditLabelState(SubWindow subwindow, ViewLabel viewLabel) {
		this.syntaxChecker = new SyntaxChecker();
		this.subwindow = subwindow;
		this.viewLabel = viewLabel;
	}
	
	public ViewLabel getViewLabel() {
		return viewLabel;
	}

	public void setViewLabel(ViewLabel viewLabel) {
		this.viewLabel = viewLabel;
	}

	public ViewComponent getCurrentViewComponent() {
		return subwindow.getSelectedComponent();
	}
	
	public void removeCharacter() {
		String label = getViewLabel().getOutput();
		
		if (label.length() > 1)
			getViewLabel().setOutput(label.substring(0, label.length() - 2) + "|");		
	}
	
	public void addCharacter(int keyCode, char keyChar) {
		getViewLabel().editLabel(keyChar);
		getViewLabel().setColor(Color.GREEN);
	}
	
	public void editLabel(char keyChar) {
		String label = getViewLabel().getOutput();
		if (label.length() > 0)
			getViewLabel().setOutput(label.substring(0, label.length() - 1) + keyChar + "|");
		else getViewLabel().setOutput(keyChar + "|");
	}
	
	public void confirmLabel() {
		enterLabel();
	}
	
	public void enterLabel() {
		Component component = getCurrentViewComponent().getComponent();
		String label = getViewLabel().getOutput();		
		getViewLabel().setColor(Color.BLACK);		
		label = label.substring(0, label.length() - 1);
		// TODO FIXME niet echt goed design
		subwindow.getViewInteraction().editLabel(component, label);
		getViewLabel().setOutput(label);	
	}

	public void addArgument() {}
}
