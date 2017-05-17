package upc.edu.cep.RDF_Model.event;

import java.util.List;

/**
 * Created by osboxes on 14/04/17.
 */
public class SimpleEvent extends Event {

    private String eventName;

    private List<Attribute> attributes;

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}