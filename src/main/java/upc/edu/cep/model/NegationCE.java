package upc.edu.cep.model;

import java.util.List;

/**
 * Created by osboxes on 14/04/17.
 */
public class NegationCP extends ComplexEvent {

    private NegationCP(List<Event> events) {
        super(events);
    }

    private NegationCP() {
        super();
    }

    @Override
    public NegationCP getInstance(List<Event> events) {
        if (events.size() > 1)
            return null;
        else
            return new NegationCP(events);
    }

    @Override
    public NegationCP getInstance() {
        return new NegationCP();
    }

    @Override
    public void addEvent(Event event) throws Exception {
        if (events.size()>=1)
        {
            throw new Exception("Wrong number of events. Not allowed more than one");
        }
        else
            events.add(event);
    }


}
