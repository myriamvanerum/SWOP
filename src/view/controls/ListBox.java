package view.controls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ListBox extends WindowControl {
	private ArrayList<String> items = new ArrayList<String>();
	private int selectedItem = -1;	

	public ListBox(ArrayList<String> items, int x, int y) {
		super();
		this.items = items;
		setX(x);
		setY(y);
		setHeight(100);
		setWidth(150);
		
		if (items.size() > 0)
			setSelectedItem(0);
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
	public void click() {
		// TODO Auto-generated method stub
	}
	
	public void add(String value) {
		ArrayList<String> items = getItems();
		items.add(value);
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
	}

	public void moveDown() {
		ArrayList<String> items = getItems();
		int index = getSelectedItem();
		
		if (index >= items.size()-1) return;
		switchItems(items, index, index+1);
		
	}

	public void moveUp() {
		ArrayList<String> items = getItems();
		int index = getSelectedItem();
		
		if (index == 0) return;
		switchItems(items, index, index-1);
	}
	
	public void switchItems(ArrayList<String> items, int i, int j) {
		String temp = items.get(j);
		items.set(j, items.get(i));
		items.set(i, temp);
		setSelectedItem(j);
	}

	public void scrollDown() {
		ArrayList<String> items = getItems();
		int index = getSelectedItem();
		
		if (index >= items.size()-1) return;
		setSelectedItem(index+1);
	}

	public void scrollUp() {
		if (getSelectedItem() == 0) return;
		setSelectedItem(getSelectedItem()-1);
	}
}
