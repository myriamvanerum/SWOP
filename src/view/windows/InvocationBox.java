package view.windows;

import java.util.ArrayList;

import domain.message.InvocationMessage;
import view.ViewInteraction;
import view.components.ViewInvocationMessage;
import view.components.ViewLabel;
import view.components.ViewMessage;
import view.formelements.Button;
import view.formelements.ListBox;
import view.formelements.TextBox;
import view.formelements.WindowControl;
import view.labelstate.InvocationState;
import view.listboxcommand.Operator;

public class InvocationBox extends DialogBox {
	private InvocationMessage message;
	private ViewMessage viewMessage;
	private Operator operator;
	
	public InvocationBox(ViewInteraction viewInteraction, ViewInvocationMessage message, int x, int y) {
		super(viewInteraction, x, y, 250, 275, new Titlebar(x,y,250));
		setTitle("Invocation message");
		this.viewMessage = message;
		this.message = (InvocationMessage) getViewMessage().getMessage();	// TODO code smell?
		addControls(getMessage());
		setViewInteraction(viewInteraction);
	}

	private void addControls(InvocationMessage message) {
		ListBox listbox = new ListBox(getMessage().getArguments(), getX()+10, getY()+130);
		ArrayList<WindowControl> controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Method: ", getX()+10, getY()+50, new ViewLabel(message.getMethod())));
		controls.add(new TextBox("New: ", getX()+10, getY()+80));
		controls.add(listbox);
		controls.add(new Button("\u02C4", getX()+175,getY()+130,25,20));
		controls.add(new Button("\u02C5", getX()+175,getY()+160,25,20));
		controls.add(new Button("X", getX()+175,getY()+190,25,20));
		controls.add(new Button("Add", getX()+175,getY()+220,25,40));
		
		operator = new Operator(listbox);
		
		setControls(controls);
		setCurrentControlIndex(0);
		getCurrentControl().changeLabelState(this);
	}
	
	public InvocationMessage getMessage() {
		return message;
	}
	
	public ViewMessage getViewMessage() {
		return viewMessage;
	}

	public void setMessage(ViewMessage viewMessage) {
		this.viewMessage = viewMessage;
	}	
	
	public Operator getOperator() {
		return operator;
	}

	@Override
	public void setDialogBoxState(ViewLabel viewLabel) {
		if (viewLabel != null)
			setLabelState(new InvocationState(this, viewLabel));
	}

	@Override
	public void confirmLabel() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().confirmLabel(getMessage());				// TODO
		}
	}
	
	@Override
	public void moveUp() {
		getOperator().moveUp();
	}
	
	@Override
	public void moveDown() {
		getOperator().moveDown();
	}
	
	@Override
	public void deleteItem() {
		getOperator().delete();
	}
}
