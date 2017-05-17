package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.RDF_Model.Operators.LogicOperator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by osboxes on 15/05/17.
 */
public class ComplexLogicEvent extends ComplexEvent {

    protected LogicOperator logicOperator;

    public ComplexLogicEvent() {
    }

    public ComplexLogicEvent(LogicOperator logicOperator, LinkedList<Event> events) {
        super(events);
        this.logicOperator = logicOperator;
    }

    public LogicOperator getLogicOperator() {
        return logicOperator;
    }

    public void setLogicOperator(LogicOperator temporalOperator) {
        this.logicOperator = temporalOperator;
    }
}
