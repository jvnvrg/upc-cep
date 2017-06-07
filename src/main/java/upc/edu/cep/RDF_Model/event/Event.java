package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;
import upc.edu.cep.RDF_Model.condition.SimpleClause;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by osboxes on 14/04/17.
 */
public class Event extends CEPElement {

    private EventSchema eventSchema;

    private List<SimpleClause> filters;


    public Event(String IRI) {
        super(IRI);
        this.filters = new ArrayList<>();
    }


    public Event() {
        super();
        this.filters = new ArrayList<>();
    }

    public EventSchema getEventSchema() {
        return eventSchema;
    }

    public void setEventSchema(EventSchema eventSchema) {
        this.eventSchema = eventSchema;
    }

    public List<SimpleClause> getFilters() {
        return filters;
    }

    public void setFilters(List<SimpleClause> filters) {
        this.filters = filters;
    }

    @Override
    public String interpret(InterpreterContext context) throws InterpreterException {
        switch (context) {
            case ESPER: {
                return eventSchema.getEventName();
            }
            default: {
                return eventSchema.getEventName();
            }
        }
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        Map<String, String> map = new HashMap<>();
        switch (context) {
            case ESPER: {
                map.put("simple event", eventSchema.getEventName());
                return map;
            }
            default: {
                map.put("simple event", eventSchema.getEventName());
                return map;
            }
        }
    }
}