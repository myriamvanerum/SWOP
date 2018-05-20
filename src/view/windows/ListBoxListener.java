package view.windows;

import java.util.ArrayList;

public interface ListBoxListener {
	void addItem(ArrayList<String> arguments);
	void deleteItem(ArrayList<String> arguments);
	void moveItemUp(ArrayList<String> arguments);
	void moveItemDown(ArrayList<String> arguments);
	void availabilityButtons(int index, int size);
}
