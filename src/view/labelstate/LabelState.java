package view.labelstate;

import java.awt.Color;

import domain.Component;
import domain.SyntaxChecker;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.windows.SubWindow;

public class LabelState {
	SyntaxChecker syntaxChecker;
	SubWindow subwindow;
	ViewLabel viewLabel;
	
	/*public LabelState(SubWindow subwindow) {
		this.syntaxChecker = new SyntaxChecker();
		this.subwindow = subwindow;
		this.viewLabel = null;
	}*/
	
	public LabelState(SubWindow subwindow, ViewLabel viewLabel) {
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
		enterLabel(getCurrentViewComponent().getComponent());
	}
	
	public void confirmLabel(Component component) {
		enterLabel(component);
	}
	
	public void enterLabel(Component component) {
		String label = getViewLabel().getOutput();		
		getViewLabel().setColor(Color.BLACK);		
		String newLabel = label.substring(0, label.length() - 1);
		// TODO interaction
		component.editLabel(subwindow.getViewInteraction().getInteraction(), newLabel);
		getViewLabel().setOutput(newLabel);	
	}
}
