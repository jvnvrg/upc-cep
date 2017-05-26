package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by osboxes on 23/05/17.
 */
public class TimeEvent extends Event {

    Timestamp timestamp;

    public TimeEvent(String IRI, Timestamp timestamp) {
        super(IRI);
        this.timestamp = timestamp;
    }

    public TimeEvent(String IRI) {
        super(IRI);
    }

    public TimeEvent(Timestamp timestamp) {
        super();
        this.timestamp = timestamp;
    }

    public TimeEvent() {
        super();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String interpret(InterpreterContext context) throws InterpreterException {
        switch (context) {
            case ESPER: {
                return timestamp.toString();
            }
            default: {
                return timestamp.toString();
            }
        }
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        Map<String, String> map = new HashMap<>();
        switch (context) {
            case ESPER: {
                map.put("time event", timestamp.toString());
                return map;
            }
            default: {
                map.put("time event", timestamp.toString());
                return map;
            }

        }
    }
}
