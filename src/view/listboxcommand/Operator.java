package view.listboxcommand;

import view.controls.ListBox;

/**
 * Operator class.
 * Uses the command pattern to identify which object will be executed
 * 
 * @author groep 03
 *
 */
public class Operator {		
	private MoveItemDown moveItemDown;
	private MoveItemUp moveItemUp;
	private DeleteItem deleteItem;
	private ScrollDown scrollDown;
	private ScrollUp scrollUp;
	
	public Operator(ListBox listbox) {
		this.moveItemDown = new MoveItemDown(listbox);
		this.moveItemUp = new MoveItemUp(listbox);
		this.deleteItem = new DeleteItem(listbox);
		this.scrollDown = new ScrollDown(listbox);
		this.scrollUp = new ScrollUp(listbox);
	}
	
	/**
	 * Command to move the selected item one place up
	 */
	public void moveUp() {
		moveItemUp.action();
	}
	
	/**
	 * Command to move the selected item one place down
	 */
	public void moveDown() {
		moveItemDown.action();
	}
	
	/**
	 * Command to delete the selected item
	 */
	public void delete() {
		deleteItem.action();
	}
	
	/**
	 * Command to select the item above the selected item
	 */
	public void scrollUp() {
		scrollUp.action();
	}
	
	/**
	 * Command to select the item beneath the selected item
	 */
	public void scrollDown() {
		scrollDown.action();
	}
}
