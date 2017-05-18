package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.RDF_Model.Operators.TemporalOperator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by osboxes on 15/05/17.
 */
public class ComplexTemporalEvent extends ComplexEvent {

    protected TemporalOperator temporalOperator;

    public ComplexTemporalEvent() {
    }

    public ComplexTemporalEvent(TemporalOperator temporalOperator, LinkedList<Event> events) {
        super(events);
        this.temporalOperator = temporalOperator;
    }

    public TemporalOperator getTemporalOperator() {
        return temporalOperator;
    }

    public void setTemporalOperator(TemporalOperator temporalOperator) {
        this.temporalOperator = temporalOperator;
    }
}
