package view.listboxcommand;

import view.controls.ListBox;

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
	
	public void moveUp() {
		moveItemUp.action();
	}
	
	public void moveDown() {
		moveItemDown.action();
	}
	
	public void delete() {
		deleteItem.action();
	}
	
	public void scrollUp() {
		scrollUp.action();
	}
	
	public void scrollDown() {
		scrollDown.action();
	}
}
