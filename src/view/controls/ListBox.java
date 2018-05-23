package view.controls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import view.windows.ListBoxListener;

/**
 * ListBox class.
 * Represents a listbox control and its content
 * @author groep 03
 */
public class ListBox extends WindowControl {
	private ArrayList<String> items = new ArrayList<String>();
	private ListBoxListener listener;
	private int selectedItem = -1;

	/**
	 * Listbox Constructor
	 * @param items
	 * 			Collection of all the listbox items
	 * @param x
	 * 			The x position of the listbox
	 * @param y
	 * 			The y position of the listbox
	 * @param listener
	 * 			The element that contains this listbox
	 */
	public ListBox(ArrayList<String> items, int x, int y, ListBoxListener listener) {
		super();
		this.items = items;
		this.listener = listener;
		setX(x);
		setY(y);
		setHeight(100);
		setWidth(150);
	}

	/* GETTERS & SETTERS */
	public ArrayList<String> getItems() {
		return items;
	}

	public void setItems(ArrayList<String> items) {
		this.items = items;
	}

	public int getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}

	public ListBoxListener getListener() {
		return listener;
	}

	public void setListener(ListBoxListener listener) {
		this.listener = listener;
	}
	
	private boolean hasItem(int index) {
		if (index > -1 && index < getItems().size())
			return true;
		return false;
	}	

	/**
	 * Draw the list items of the listbox
	 * @param g
	 * 			Graphics class
	 */
	private void drawItems(Graphics2D g) {
		int y = getY();
		int start = 0, end = items.size();
		
		if (items.size() > 5) {
			end = start + 5;
			
			if (selectedItem > 4) {
				start = selectedItem - 4;
				end = selectedItem + 1;
			}
		}
		
		for (int i = start; i < end; i++) {
			if (i == selectedItem) {
				g.setColor(Color.GRAY);
				Rectangle rectangle = new Rectangle(getX(), y, 150 - 2, 20 - 2);
				g.draw(rectangle);
				g.fill(rectangle);
				g.setColor(Color.BLACK);
			}
	
			String item = items.get(i);
			int stringHeight = (int) g.getFontMetrics().getStringBounds(item, g).getHeight();
			g.drawString(items.get(i), getX() + 5, y + stringHeight);
			y += 20;
		}
	}

	/**
	 * Draw the window control
	 * @param g
	 * 			Graphics class
	 */
	@Override
	public void draw(Graphics2D g) {
		g.drawString("Listbox: ", getX(), getY() - 10);
		Rectangle rectangle = new Rectangle(getX() - 1, getY() - 1, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.fill(rectangle);
		g.setColor(Color.BLACK);
		drawItems(g);
		
		if (isActive())
			g.setColor(new Color(255, 145, 70));
		
		g.draw(rectangle);
		g.setColor(Color.BLACK);
	}
	
	/**
	 * Update the control data
	 * @param items
	 */
	@Override
	public void update(ArrayList<String> items) {
		setItems(items);
	}
	
	/**
	 * Add a new list item to the listbox
	 * @param textBox
	 * 			The textbox that contains the new list item
	 */
	public void add(TextBox textBox) {
		if (!textBox.add()) return;		
		getListener().updateArguments(getItems());
		setAvailabilityButtons();
	}

	/**
	 * Remove a list item from the listbox
	 */
	public void remove() {
		int removeIndex = getSelectedItem();
				
		if (getItems().size() <= 0) return;			
		if (getItems().size() > 1)
			setSelectedItem(0);
		else 
			setSelectedItem(-1);
			
		getItems().remove(removeIndex);	
		
		getListener().updateArguments(getItems());
		setAvailabilityButtons();
	}

	/**
	 * Move the selected item to the next position
	 */
	public void moveDown() {	
		if (getSelectedItem() >= getItems().size()-1) return;
		switchItems(getItems(), getSelectedItem(), getSelectedItem()+1);
		
		getListener().updateArguments(getItems());
		setAvailabilityButtons();
		
	}

	/**
	 * Move the selected item to the previous position
	 */
	public void moveUp() {		
		if (getSelectedItem() == 0) return;
		switchItems(getItems(), getSelectedItem(), getSelectedItem()-1);
		
		getListener().updateArguments(getItems());
		setAvailabilityButtons();
	}
	
	/**
	 * Switch the position of two list items 
	 * @param items
	 * 			The collection of all list items
	 * @param i
	 * 			The index of the first item
	 * @param j
	 * 			The index of the second item
	 */
	public void switchItems(ArrayList<String> items, int i, int j) {
		String temp = items.get(j);
		items.set(j, items.get(i));
		items.set(i, temp);
		setSelectedItem(j);
	}

	/**
	 * Select the next list item
	 */
	public void scrollDown() {		
		if (getSelectedItem() >= getItems().size()-1) return;
		setSelectedItem(getSelectedItem()+1);
		
		setAvailabilityButtons();
	}

	/**
	 * Select the previous list item
	 */
	public void scrollUp() {
		if (getSelectedItem() == 0) return;
		setSelectedItem(getSelectedItem()-1);
		
		setAvailabilityButtons();
	}
	
	public void setAvailabilityButtons() {
		getListener().availabilityButtons(getSelectedItem(), getItems().size());
	}
	
	/**
	 * Check if the listbox is clicked and set the current selected list item
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 * @return A windowcontrol if it's clicked
	 * 			Null if no windowcontrol is clicked
	 */
	@Override
	public WindowControl click(int x, int y) {
		if (x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight())
		{
			int index = (y-getY()) / 20;
			
			if(hasItem(index))
				setSelectedItem(Math.abs(index));
			
			setAvailabilityButtons();
			
			return this;
		}
		
		return null;
	}
}