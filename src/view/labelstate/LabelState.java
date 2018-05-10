package view.labelstate;

import java.awt.Color;

import domain.Component;
import domain.SyntaxChecker;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.windows.DiagramWindow;

public class LabelState {
	SyntaxChecker syntaxChecker;
	DiagramWindow subwindow;
	
	public LabelState(DiagramWindow subwindow) {
		this.syntaxChecker = new SyntaxChecker();
		this.subwindow = subwindow;
	}
	
	public ViewComponent getCurrentViewComponent() {
		return subwindow.getSelectedComponent();
	}
	
	public ViewLabel getCurrentViewLabel() {
		return getCurrentViewComponent().getViewLabel();
	}
	
	public void setLabelColor() {
		getCurrentViewLabel().setColor(Color.GREEN);
	}
	
	public void removeCharacter() {
		String label = getCurrentViewLabel().getOutput();
		
		if (label.length() > 1)
			getCurrentViewLabel().setOutput(label.substring(0, label.length() - 2) + "|");		
	}
	
	public void addCharacter(int keyCode, char keyChar) {
		editLabel(keyChar);
		setLabelColor();
	}
	
	public void editLabel(char keyChar) {
		String label = getCurrentViewLabel().getOutput();
		if (label.length() > 0)
			getCurrentViewLabel().setOutput(label.substring(0, label.length() - 1) + keyChar + "|");
		else getCurrentViewLabel().setOutput(keyChar + "|");
	}
	
	public void confirmLabel() {
		enterLabel(getCurrentViewComponent().getComponent(), getCurrentViewComponent());
	}
	
	public void enterLabel(Component component, ViewComponent viewComponent) {
		ViewLabel viewLabel = getCurrentViewLabel();
		String label = getCurrentViewLabel().getOutput();
		
		viewLabel.setColor(Color.BLACK);
		
		String newLabel = label.substring(0, label.length() - 1);
		component.editLabel(subwindow.getViewInteraction().getInteraction(), newLabel);
		viewLabel.setOutput(newLabel);	
	}
}
