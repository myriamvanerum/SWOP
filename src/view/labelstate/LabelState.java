package view.labelstate;

import java.awt.Color;

import domain.Component;
import domain.SyntaxChecker;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.windows.DiagramWindow;

public class LabelState {
	SyntaxChecker syntaxChecker;
	DiagramWindow diagram;
	
	public LabelState(DiagramWindow diagram) {
		this.syntaxChecker = new SyntaxChecker();
		this.diagram = diagram;
	}
	
	public ViewComponent getCurrentViewComponent() {
		return diagram.getSelectedComponent();
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
		component.editLabel(diagram.getViewInteraction().getInteraction(), newLabel);
		viewLabel.setOutput(newLabel);	
	}
}
