package domain;

/**
 * Component Class. 
 * Parent class for Parties and Messages
 * @author groep 03
 */
public class Component {

    public String label;
    
    /**
	 * Remove the component from the interaction
	 * @param interaction
	 * 		The interaction the component should be removed from
	 */
    public void remove(Interaction interaction) {}
            
    /* GETTERS AND SETTERS */

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }
    
    /**
	 * Edit the Label of a Component
	 * @param label
	 * 		The new label of a component
	 */
	public Boolean editLabel(String label) {return false;}
}
