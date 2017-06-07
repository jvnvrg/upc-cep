package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by osboxes on 14/04/17.
 */
public class EventSchema extends CEPElement {

    private String eventName;

    private String topicName;

    private List<Attribute> attributes;


    public EventSchema(String IRI, String eventName, List<Attribute> attributes) {
        super(IRI);
        this.eventName = eventName;
        this.attributes = attributes;
    }

    public EventSchema(String eventName, List<Attribute> attributes) {
        super();
        this.eventName = eventName;
        this.attributes = attributes;
    }

    public EventSchema(String IRI) {
        super(IRI);
        this.attributes = new ArrayList<>();
    }

    public EventSchema() {
        super();
        this.attributes = new ArrayList<>();
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
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


    @Override
    public String interpret(InterpreterContext context) throws InterpreterException {
        switch (context) {
            case ESPER: {
                return eventName;
            }
            default: {
                return eventName;
            }
        }
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        Map<String, String> map = new HashMap<>();
        switch (context) {
            case ESPER: {
                map.put("simple event", eventName);
                return map;
            }
            default: {
                map.put("simple event", eventName);
                return map;
            }
        }
    }
}