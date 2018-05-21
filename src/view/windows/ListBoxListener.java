package view.windows;

import java.util.ArrayList;

public interface ListBoxListener {
	void updateArguments(ArrayList<String> arguments);
	void availabilityButtons(int index, int size);
}
