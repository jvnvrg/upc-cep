package upc.edu.cep.RDF_Model.event;

/**
 * Created by osboxes on 14/04/17.
 */
public abstract class Event {
    protected String IRI;

    public Event(String IRI) {
        this.IRI = IRI;
    }

    public Event() {
    }

    public String getIRI() {
        return IRI;
    }

    public void setIRI(String IRI) {
        this.IRI = IRI;
    }
}
