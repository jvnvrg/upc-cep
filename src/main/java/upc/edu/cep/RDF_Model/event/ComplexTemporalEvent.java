package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.RDF_Model.Operators.TemporalOperator;

import java.util.LinkedList;

/**
 * Created by osboxes on 15/05/17.
 */
public class ComplexTemporalEvent extends ComplexEvent {

    protected TemporalOperator temporalOperator;

    public ComplexTemporalEvent() {
        super();
    }

    public ComplexTemporalEvent(TemporalOperator temporalOperator, LinkedList<Event> events) {
        super(events);
        this.temporalOperator = temporalOperator;
    }

    public ComplexTemporalEvent(String IRI) {
        super(IRI);
    }

    public ComplexTemporalEvent(TemporalOperator temporalOperator, LinkedList<Event> events, String IRI) {
        super(events, IRI);
        this.temporalOperator = temporalOperator;
    }

    public TemporalOperator getTemporalOperator() {
        return temporalOperator;
    }

    public void setTemporalOperator(TemporalOperator temporalOperator) {
        this.temporalOperator = temporalOperator;
    }
}
