package upc.edu.cep.model.condition;

/**
 * Created by osboxes on 18/04/17.
 */
public class EventAttributeOperand extends Operand {

    private String eventName;
    private String attributeName;

    public EventAttributeOperand(String eventName, String attributeName) {
        this.eventName = eventName;
        this.attributeName = attributeName;
    }

    public EventAttributeOperand() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
