package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;
import upc.edu.cep.RDF_Model.condition.Operand;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by osboxes on 14/05/17.
 */
public class Attribute extends Operand {

    private String name;
    private AttributeType attributeType;
    private AtomicEvent event;

    public Attribute() {
        super();
    }

    public Attribute(String name, AttributeType attributeType, AtomicEvent event) {
        super();
        this.name = name;
        this.event = event;
    }

    public Attribute(String IRI) {
        super(IRI);
    }

    public Attribute(String name, AttributeType attributeType, String IRI, AtomicEvent event) {
        super(IRI);
        this.name = name;
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public AtomicEvent getEvent() {
        return event;
    }

    public void setEvent(AtomicEvent event) {
        this.event = event;
    }

    @Override
    public String interpret(InterpreterContext context) throws InterpreterException {
        switch (context) {
            case ESPER: {
                return event.interpret(context) + "." + name;
            }
            default: {
                return event.interpret(context) + "." + name;
            }
        }
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        Map<String, String> map = new HashMap<>();
        switch (context) {
            case ESPER: {
                map.put("attribute", event.interpret(context) + "." + name);
                return map;
            }
            default: {
                map.put("attribute", event.interpret(context) + "." + name);
                return map;
            }
        }
    }
}
