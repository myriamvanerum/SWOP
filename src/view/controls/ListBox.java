package view.controls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import view.components.ViewLabel;
import view.windows.ListBoxListener;

public class ListBox extends WindowControl {
	private ArrayList<String> items = new ArrayList<String>();
	private ListBoxListener listener;
	private int selectedItem = -1;	

	public ListBox(ArrayList<String> items, int x, int y, ListBoxListener listener) {
		super();
		this.items = items;
		this.listener = listener;
		setX(x);
		setY(y);
		setHeight(100);
		setWidth(150);
	}

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
	
	@Override
	public void update(ArrayList<String> items) {
		setItems(items);
	}

	@Override
	public void click() {}
	
	public void add(TextBox textBox) {
		String value = textBox.getValue();
		
		if (value.trim().length() > 0) {
			textBox.getState().addArgument();			
			getItems().add(value);
			getListener().availabilityButtons(getSelectedItem(), getItems().size());			
		}
	}

	public void remove() {
		ArrayList<String> items = getItems();
		int index = getSelectedItem();
				
		if (items.size() <= 0) return;			
		if (items.size() > 1)
			setSelectedItem(0);
		else 
			setSelectedItem(-1);
			
		items.remove(index);	
		
		getListener().updateArguments(getItems());
		getListener().availabilityButtons(getSelectedItem(), getItems().size());
	}

	public void moveDown() {
		ArrayList<String> items = getItems();
		int index = getSelectedItem();
		
		if (index >= items.size()-1) return;
		switchItems(items, index, index+1);
		
		getListener().updateArguments(getItems());
		getListener().availabilityButtons(getSelectedItem(), getItems().size());
		
	}

	public void moveUp() {
		int index = getSelectedItem();
		
		if (index == 0) return;
		switchItems(getItems(), index, index-1);
		
		getListener().updateArguments(getItems());
		getListener().availabilityButtons(getSelectedItem(), getItems().size());
	}
	
	public void switchItems(ArrayList<String> items, int i, int j) {
		String temp = items.get(j);
		items.set(j, items.get(i));
		items.set(i, temp);
		setSelectedItem(j);
	}

	public void scrollDown() {
		int index = getSelectedItem();
		
		if (index >= getItems().size()-1) return;
		setSelectedItem(index+1);
		
		getListener().availabilityButtons(getSelectedItem(), getItems().size());
	}

	public void scrollUp() {
		if (getSelectedItem() == 0) return;
		setSelectedItem(getSelectedItem()-1);
		getListener().availabilityButtons(getSelectedItem(), getItems().size());
	}
}