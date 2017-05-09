package upc.edu.cep.model;

import upc.edu.cep.model.action.Action;
import upc.edu.cep.model.condition.NonTemporalCondition;
import upc.edu.cep.model.condition.TemporalCondition;
import upc.edu.cep.model.event.Event;

/**
 * Created by osboxes on 20/04/17.
 */
public class Rule {

    NonTemporalCondition nonTemporalCondition;
    TemporalCondition temporalCondition;
    Event event;
    Action action;

    public Rule() {
    }

    public Rule(NonTemporalCondition nonTemporalCondition, TemporalCondition temporalCondition, Event event, Action action) {
        this.nonTemporalCondition = nonTemporalCondition;
        this.temporalCondition = temporalCondition;
        this.event = event;
        this.action = action;
    }

    public Rule(NonTemporalCondition nonTemporalCondition, Event event, Action action) {
        this.nonTemporalCondition = nonTemporalCondition;
        this.event = event;
        this.action = action;
    }

    public NonTemporalCondition getNonTemporalCondition() {
        return nonTemporalCondition;
    }

    public void setNonTemporalCondition(NonTemporalCondition nonTemporalCondition) {
        this.nonTemporalCondition = nonTemporalCondition;
    }

    public TemporalCondition getTemporalCondition() {
        return temporalCondition;
    }

    public void setTemporalCondition(TemporalCondition temporalCondition) {
        this.temporalCondition = temporalCondition;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
