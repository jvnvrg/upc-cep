package upc.edu.cep.RDF_Model;

import upc.edu.cep.Interpreter.Interpreter;
import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;
import upc.edu.cep.RDF_Model.action.Action;
import upc.edu.cep.RDF_Model.condition.Condition;
import upc.edu.cep.RDF_Model.event.Event;
import upc.edu.cep.RDF_Model.window.Window;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by osboxes on 20/04/17.
 */
public class Rule implements Interpreter {

    String IRI;
    Condition condition;
    Event event;
    Action action;
    Window window;

    public Rule() {
    }

    public Rule(Condition condition, Event event, Action action, Window window, String IRI) {
        this.IRI = IRI;
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

    public String getIRI() {
        return IRI;
    }

    public void setIRI(String IRI) {
        this.IRI = IRI;
    }

    @Override
    public String interpret(InterpreterContext context) throws InterpreterException {
        switch (context) {
            case ESPER: {
                String rule = "select ";
                rule += action.interpret(context);
                rule += " from pattern [every " + event.interpret(context) + "]" + window.interpret(context) + " ";
                Map<String, String> cons = condition.interpretToMap(context);
                if (cons.get("where") != null) {
                    rule += "where " + cons.get("where") + " ";
                }
                if (cons.get("group by") != null) {
                    rule += "group by " + cons.get("groupby") + " ";
                }
                if (cons.get("having") != null) {
                    rule += "having " + cons.get("having") + " ";
                }
                break;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        Map<String, String> rule = new HashMap<>();
        switch (context) {
            case ESPER: {
                rule.put("rule", interpret(context));
                break;
            }
        }
        return rule;
    }
}
