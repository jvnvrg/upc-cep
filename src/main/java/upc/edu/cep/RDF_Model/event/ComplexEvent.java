package upc.edu.cep.RDF_Model.event;

import java.util.LinkedList;

/**
 * Created by osboxes on 14/04/17.
 */
public abstract class ComplexEvent extends Event {

    protected LinkedList <Event> events;

    public ComplexEvent(LinkedList<Event> events) {
        super();
        this.events = events;
    }

    public ComplexEvent() {
        super();
        events = new LinkedList<>();
    }

    public ComplexEvent(LinkedList<Event> events, String IRI) {
        super(IRI);
        this.events = events;
    }

    public ComplexEvent(String IRI) {
        super(IRI);
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
