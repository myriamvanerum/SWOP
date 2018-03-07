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

    @Override
    public Boolean focused() {
        return focused;
    }

    @Override
    public void focus() {
        focused = true;
    }

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
