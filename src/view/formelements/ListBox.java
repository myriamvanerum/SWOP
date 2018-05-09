package view.formelements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ListBox extends WindowControl {
	private ArrayList<String> items = new ArrayList<String>();
	private int selectedItem = -1;
	//private String description;
	private int height = 100;
	private int width = 150;
	
	public ListBox(ArrayList<String> items, Point2D position) {
		this.items = items;
		//this.description = description;
		this.position = position;
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

	private void drawItems(Graphics2D g, Point2D position) {
		int x = (int) position.getX();
		int y = (int) position.getY();
		
		for (int i = 0; i < items.size(); i++) {
			if (i == selectedItem) {
				g.setColor(Color.GRAY);
				Rectangle rectangle = new Rectangle(x, y, 150-2, 20-2);
				g.draw(rectangle);
				g.fill(rectangle);
				g.setColor(Color.BLACK);
			}
			
			String item = items.get(i);			
			int stringHeight = (int)g.getFontMetrics().getStringBounds(item, g).getHeight();			
			g.drawString(items.get(i), x+5, y+stringHeight);
			y += 20;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		int x = (int) position.getX();
		int y = (int) position.getY();
		
		g.drawString("Listbox: ", x, y-10);		
		Rectangle rectangle = new Rectangle(x-1, y-1, width, height);
		g.setColor(Color.WHITE);
		g.fill(rectangle);	
		g.setColor(Color.BLACK);	
		drawItems(g, position);
		g.draw(rectangle);
	}
}
