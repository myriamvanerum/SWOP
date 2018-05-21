package view.windows;

import java.util.ArrayList;

import domain.Component;
import domain.message.InvocationMessage;
import view.ViewInteraction;
import view.components.ViewInvocationMessage;
import view.components.ViewLabel;
import view.components.ViewMessage;
import view.controls.Button;
import view.controls.ListBox;
import view.controls.TextBox;
import view.controls.WindowControl;
import view.labelstate.EditInvocationMessageMethodState;
import view.labelstate.EditInvocationMessageArgumentState;
import view.listboxcommand.AddItem;
import view.listboxcommand.DeleteItem;
import view.listboxcommand.MoveItemDown;
import view.listboxcommand.MoveItemUp;
import view.listboxcommand.Operator;

/**
 * InvocationBox class.
 * Represents a dialogbox used for editing the characteristics of a invocation message, subclass of the DialogBox class
 * @author groep 03
 *
 */
public class InvocationBox extends DialogBox implements ListBoxListener {
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

	/**
	 * Define all the controls of an invocation box
	 * @param message
	 * 			The invocation message that is being edited
	 */
	private void addControls(InvocationMessage message) {
		ListBox listbox = new ListBox(getMessage().getArguments(), getX()+10, getY()+130, this);
		ArrayList<WindowControl> controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Method: ", getX()+10, getY()+50, new ViewLabel(message.getMethod()), new EditInvocationMessageMethodState(this, null, message)));
		TextBox add = new TextBox("New: ", getX()+10, getY()+80, new EditInvocationMessageArgumentState(this, null, getMessage()));
		controls.add(add);
		controls.add(new Button("Add", getX()+175,getY()+220,25,40, new AddItem(listbox, add), true, new int[]{-2,-2}));
		controls.add(listbox);
		controls.add(new Button("\u02C4", getX()+175,getY()+130,25,20, new MoveItemUp(listbox), false, new int[]{0,1}));
		controls.add(new Button("\u02C5", getX()+175,getY()+160,25,20, new MoveItemDown(listbox), false, new int[]{-1}));
		controls.add(new Button("X", getX()+175,getY()+190,25,20, new DeleteItem(listbox), false, new int[]{-1,0}));
		
		operator = new Operator(listbox);
		
		setControls(controls);
		setCurrentControlIndex(0);
		getCurrentControl().setActive(true);
		getCurrentControl().currentControl(this);
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
	
	/**
	 * Confirm that the entered label should be set as new label
	 */
	@Override
	public void confirmLabel() {
		if (!editingLabel()) return;
		getLabelState().setViewLabel(getCurrentViewLabel());
		getLabelState().confirmLabel();				// TODO
	}
	
	/**
	 * Arrow up key is pressed, selected the item in the listbox above the selected item
	 */
	@Override
	public void scrollUp() {
		getOperator().scrollUp();
	}
	
	/**
	 * Arrow down key is pressed, selected the item in the listbox underneath the selected item
	 */
	@Override
	public void scrollDown() {
		getOperator().scrollDown();
	}	

	/**
	 * Activate the selected control
	 */
	@Override
	public void pressSpace() {
		getCurrentControl().space();
	}
	
	@Override
	public void editViewLabel(Component component) {
		for (WindowControl control : controls)
			control.update(getMessage().getArguments());
	}
	
	@Override
	public void availabilityButtons(int value, int limit) {
		for (WindowControl control : getControls()) {
			control.checkAvailability(value, limit);
		}
	}

	@Override
	public void updateArguments(ArrayList<String> arguments) {
		getMessage().setArguments(arguments);
	}
}
