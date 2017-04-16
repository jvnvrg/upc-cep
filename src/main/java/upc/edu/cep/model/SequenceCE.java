package upc.edu.cep.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by osboxes on 15/04/17.
 */
public class SequenceCE extends ComplexEvent {

    private SequenceCE(List<Event> events) {
        super(events);
    }

    private SequenceCE() {
        super();
    }

    @Override
    public SequenceCE getInstance(List<Event> events) {
        events = new LinkedList<>();
        return new SequenceCE(events);
    }

    @Override
    public SequenceCE getInstance() {
        return new SequenceCE();
    }

    @Override
    public void addEvent(Event event) throws Exception {
        events.add(event);
    }

    public void addEvent(Event event, int index) throws Exception {
        events.set(index, event);
    }
}
