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
public class SimpleEvent extends Event {

    private AtomicEvent atomicEvent;

    private List<SimpleClause> filters;


    public SimpleEvent(String IRI) {
        super(IRI);
        this.filters = new ArrayList<>();
    }


    public SimpleEvent() {
        super();
        this.filters = new ArrayList<>();
    }

    public AtomicEvent getAtomicEvent() {
        return atomicEvent;
    }

    public void setAtomicEvent(AtomicEvent atomicEvent) {
        this.atomicEvent = atomicEvent;
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
                return atomicEvent.getEventName();
            }
            default: {
                return atomicEvent.getEventName();
            }
        }
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        Map<String, String> map = new HashMap<>();
        switch (context) {
            case ESPER: {
                map.put("simple event", atomicEvent.getEventName());
                return map;
            }
            default: {
                map.put("simple event", atomicEvent.getEventName());
                return map;
            }
        }
    }
}