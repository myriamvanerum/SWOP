package model;

/**
 * DiagramComponent implements the interface Focusable.
 * 
 * @author groep 03
 *
 */
public class DiagramComponent implements Focusable {

    private boolean focused;
    protected Label label;

    /**
     * This method determines if the object is focused
     */
    @Override
    public Boolean focused() {
        return focused;
    }

    /**
     * This method focuses an object
     */
    @Override
    public void focus() {
        focused = true;
    }

    /**
     * This method unfocuses an object
     */
    @Override
    public void unfocus() {
        focused = false;
    }    

    public Label getLabel(){
        return label;
    }

    public void setLabel(Label label){
        this.label = label;
    }
}
