package view.windows;

import java.util.ArrayList;

import domain.message.InvocationMessage;
import view.components.ViewLabel;
import view.formelements.Button;
import view.formelements.ListBox;
import view.formelements.TextBox;
import view.formelements.WindowControl;

public class InvocationBox extends DialogBox {
	private InvocationMessage message;
	private ViewLabel labelMethod;
	private ViewLabel labelArguments;
	
	public InvocationBox(InvocationMessage message, int x, int y) {
		super(x, y, 250, 275, new Titlebar(x,y,250));
		this.message = message;	
		this.labelMethod = new ViewLabel(message.getMethod());
		this.labelArguments = new ViewLabel("");
		setTitle("Invocation message");
		addControls(labelMethod);
	}

	private void addControls(ViewLabel method) {
		ArrayList<WindowControl> controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Method: ", getX()+10, getY()+20, method));
		controls.add(new TextBox("New: ", getX()+10, getY()+20, labelArguments));
		controls.add(new ListBox(message.getArguments(), getX()+10, getY()+100));
		controls.add(new Button("\u02C4", getX()+175,getY()+100,25,20));
		controls.add(new Button("\u02C5", getX()+175,getY()+130,25,20));
		controls.add(new Button("X", getX()+175,getY()+160,25,20));
		controls.add(new Button("Add", getX()+175,getY()+190,25,20));
		
		setControls(controls);
		setCurrentControl(0);
	}
}
