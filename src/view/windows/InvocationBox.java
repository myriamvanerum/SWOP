package view.windows;

import java.util.ArrayList;

import domain.message.InvocationMessage;
import view.ViewInteraction;
import view.components.ViewLabel;
import view.formelements.Button;
import view.formelements.ListBox;
import view.formelements.TextBox;
import view.formelements.WindowControl;
import view.labelstate.InvocationState;

public class InvocationBox extends DialogBox {
	private InvocationMessage message;
	private ViewLabel labelMethod;
	private ViewLabel labelArguments;
	
	public InvocationBox(ViewInteraction viewInteraction, InvocationMessage message, int x, int y) {
		super(viewInteraction, x, y, 250, 275, new Titlebar(x,y,250));
		this.message = message;	
		this.labelMethod = new ViewLabel(message.getMethod());
		this.labelArguments = new ViewLabel("");
		setTitle("Invocation message");
		addControls(labelMethod);
		setLabelState(new InvocationState(this, labelMethod));
		setCurrentViewLabel(labelMethod);
		setViewInteraction(viewInteraction);
	}

	private void addControls(ViewLabel method) {
		ArrayList<WindowControl> controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Method: ", getX()+10, getY()+50, method));
		controls.add(new TextBox("New: ", getX()+10, getY()+80, labelArguments));
		controls.add(new ListBox(message.getArguments(), getX()+10, getY()+130));
		controls.add(new Button("\u02C4", getX()+175,getY()+130,25,20));
		controls.add(new Button("\u02C5", getX()+175,getY()+160,25,20));
		controls.add(new Button("X", getX()+175,getY()+190,25,20));
		controls.add(new Button("Add", getX()+175,getY()+220,25,40));
		
		setControls(controls);
		setCurrentControl(0);
	}
	
	public InvocationMessage getMessage() {
		return message;
	}

	public void setMessage(InvocationMessage message) {
		this.message = message;
	}

	@Override
	public void confirmLabel() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().confirmLabel(getMessage());
		}
	}
}
