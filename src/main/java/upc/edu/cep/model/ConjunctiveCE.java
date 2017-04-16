package upc.edu.cep.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osboxes on 15/04/17.
 */
public class ConjunctiveCE extends ComplexEvent{

    private ConjunctiveCE(List<Event> events) {
        super(events);
    }

    private ConjunctiveCE() {
        super();
    }

    @Override
    public ConjunctiveCE getInstance(List<Event> events) {
        events = new ArrayList<>();
        return new ConjunctiveCE(events);
    }

    @Override
    public ConjunctiveCE getInstance() {
        return new ConjunctiveCE();
    }

    @Override
    public void addEvent(Event event) throws Exception {
        events.add(event);
    }

    public void addEvent(Event event, int index) throws Exception {
        events.set(index, event);
    }
}
