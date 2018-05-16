package view.listboxcommand;

import view.formelements.ListBox;

public class Operator {		
	private MoveItemDown moveItemDown;
	private MoveItemUp moveItemUp;
	private DeleteItem deleteItem;
	
	public Operator(ListBox listbox) {
		this.moveItemDown = new MoveItemDown(listbox);
		this.moveItemUp = new MoveItemUp(listbox);
		this.deleteItem = new DeleteItem(listbox);
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
}
