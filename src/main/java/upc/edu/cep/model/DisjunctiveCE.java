package upc.edu.cep.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osboxes on 15/04/17.
 */
public class DisjunctiveCE extends ComplexEvent {

    private DisjunctiveCE(List<Event> events) {
        super(events);
    }

    private DisjunctiveCE() {
        super();
    }

    @Override
    public DisjunctiveCE getInstance(List<Event> events) {
        events = new ArrayList<>();
        return new DisjunctiveCE(events);
    }

    @Override
    public DisjunctiveCE getInstance() {
        return new DisjunctiveCE();
    }

    @Override
    public void addEvent(Event event) throws Exception {
        events.add(event);
    }

    public void addEvent(Event event, int index) throws Exception {
        events.set(index, event);
    }
}
