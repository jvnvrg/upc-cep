package upc.edu.cep.RDF_Model.event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by osboxes on 14/04/17.
 */
public abstract class ComplexEvent extends Event {

    protected LinkedList <Event> events;

    public ComplexEvent(LinkedList<Event> events) {
        this.events = events;
    }

    public ComplexEvent() {
        events = new LinkedList<>();
    }

    public void addEvents(Event event) {
        events.add(event);
    }

    public LinkedList<Event> getEvents() {
        return events;
    }

    public void setEvents(LinkedList<Event> events) {
        this.events = events;
    }

//    public abstract ComplexEvent getInstance(List<Event> events);

//    public abstract ComplexEvent getInstance();

//    public abstract void addEvent(Event event) throws Exception;
}
