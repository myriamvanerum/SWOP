package view.formelements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ListBox extends WindowControl {
	private ArrayList<String> items = new ArrayList<String>();
	private int selectedItem = -1;


	public ListBox(ArrayList<String> items, int x, int y) {
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
		for (int i = 0; i < items.size(); i++) {
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
		g.draw(rectangle);
	}
}
