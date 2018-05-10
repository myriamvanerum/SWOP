package view.windows;

import java.util.ArrayList;

import view.components.ViewInvocationMessage;
import view.formelements.Button;
import view.formelements.ListBox;
import view.formelements.TextBox;
import view.formelements.WindowControl;

public class InvocationBox extends DialogBox {
	private ViewInvocationMessage message;
	
	public InvocationBox(ViewInvocationMessage message, int x, int y) {
		super(x, y, 250, 275, new Titlebar(x,y,250));
		this.message = message;	
		addControls();
	}

	private void addControls() {
		ArrayList<WindowControl> controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Method: ", getX()+10, getY()+20, message.getViewLabel()));
		controls.add(new TextBox("New: ", getX()+10, getY()+20, message.getViewLabel()));
		controls.add(new ListBox(new ArrayList<String>(), getX()+10, getY()+100));
		controls.add(new Button("\u02C4", getX()+175,getY()+100,25,20));
		controls.add(new Button("\u02C5", getX()+175,getY()+130,25,20));
		controls.add(new Button("X", getX()+175,getY()+160,25,20));
		controls.add(new Button("Add", getX()+175,getY()+190,25,20));
		
		setControls(controls);
		setCurrentControl(0);
	}
}
