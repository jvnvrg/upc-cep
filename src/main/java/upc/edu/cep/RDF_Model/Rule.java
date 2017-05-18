package upc.edu.cep.RDF_Model;

import upc.edu.cep.RDF_Model.action.Action;
import upc.edu.cep.RDF_Model.event.Event;
import upc.edu.cep.RDF_Model.window.Window;
import upc.edu.cep.model.condition.Condition;


/**
 * Created by osboxes on 20/04/17.
 */
public class Rule {

    Condition condition;
    Event event;
    Action action;
    Window window;

    public Rule() {
    }

    public Rule(Condition condition, Event event, Action action, Window window) {
        this.condition = condition;
        this.event = event;
        this.action = action;
        this.window = window;
    }

    public Condition getCondition() {
        return this.condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
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

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}
