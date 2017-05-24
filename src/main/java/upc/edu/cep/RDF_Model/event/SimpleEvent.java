package upc.edu.cep.RDF_Model.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osboxes on 14/04/17.
 */
public class SimpleEvent extends Event {

    private String eventName;

    private List<Attribute> attributes;


    public SimpleEvent(String IRI, String eventName, List<Attribute> attributes) {
        super(IRI);
        this.eventName = eventName;
        this.attributes = attributes;
    }

    public SimpleEvent(String eventName, List<Attribute> attributes) {
        super();
        this.eventName = eventName;
        this.attributes = attributes;
    }

    public SimpleEvent(String IRI) {
        super(IRI);
        this.attributes = new ArrayList<>();
    }

    public SimpleEvent() {
        super();
        this.attributes = new ArrayList<>();
    }

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