package upc.edu.cep.model.event;

import java.util.List;

/**
 * Created by osboxes on 14/04/17.
 */
public abstract class ComplexEvent {

    List<Event> events;

    public ComplexEvent(List<Event> events) {
        this.events = events;
    }

    public ComplexEvent() {
        //events = new ArrayList<>();
    }

    public void addEvents(Event event) {
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public abstract ComplexEvent getInstance(List<Event> events);

    public abstract ComplexEvent getInstance();

    public abstract void addEvent(Event event) throws Exception;
}
