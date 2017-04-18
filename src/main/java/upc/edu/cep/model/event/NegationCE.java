package upc.edu.cep.model.event;

import java.util.List;

/**
 * Created by osboxes on 14/04/17.
 */
public class NegationCE extends ComplexEvent {

    private NegationCE(List<Event> events) {
        super(events);
    }

    private NegationCE() {
        super();
    }

    @Override
    public NegationCE getInstance(List<Event> events) {
        if (events.size() > 1)
            return null;
        else
            return new NegationCE(events);
    }

    @Override
    public NegationCE getInstance() {
        return new NegationCE();
    }

    @Override
    public void addEvent(Event event) throws Exception {
        if (events.size() >= 1) {
            throw new Exception("Wrong number of events. Not allowed more than one");
        } else {
            events.add(event);
        }
    }

}
