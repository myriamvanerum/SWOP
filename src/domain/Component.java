package domain;

import java.awt.*;

public class Component {

    private boolean isSelected = false;

    public Boolean isSelected(){
        return isSelected;
    }

    public void setSelected(){
        this.isSelected = true;
    }

    public void unselect(){
        this.isSelected = false;
    }

    public Color getSelectedColor(){

       return  new Color(159, 236, 249);
    }

    public Color getDefaultColor(){
        return new Color(0, 0, 0);
    }

}
