package view.labelstate;

import java.awt.Color;

import model.Component;
import model.SyntaxChecker;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.windows.SubWindow;

public class LabelState {
	SyntaxChecker syntaxChecker;
	SubWindow subwindow;
	
	public LabelState(SubWindow subwindow) {
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
		getCurrentViewLabel().setOutput(label.substring(0, label.length() - 1) + keyChar + "|");
	}
	
	public void confirmLabel() {
		enterLabel(getCurrentViewComponent().getComponent(), getCurrentViewComponent());
	}
	
	public void enterLabel(Component component, ViewComponent viewComponent) {
		ViewLabel viewLabel = getCurrentViewLabel();
		String label = getCurrentViewLabel().getOutput();
		
		viewLabel.setColor(Color.BLACK);
		
		String newLabel = label.substring(0, label.length() - 1);
		component.editLabel(subwindow.getInteractionManager().getInteraction(), newLabel);
		viewLabel.setOutput(newLabel);	
	}
}
