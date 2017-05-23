package upc.edu.cep.RDF_Model.event;

import java.security.Timestamp;

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
}
