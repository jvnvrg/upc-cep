package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;
import upc.edu.cep.RDF_Model.Operators.LogicOperator;
import upc.edu.cep.RDF_Model.Operators.LogicOperatorEnum;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by osboxes on 15/05/17.
 */
public class ComplexLogicEvent extends ComplexEvent {

    protected LogicOperator logicOperator;

    public ComplexLogicEvent() {
        super();
    }

    public ComplexLogicEvent(LogicOperator logicOperator, LinkedList<Event> events) {
        super(events);
        this.logicOperator = logicOperator;
    }

    public ComplexLogicEvent(String IRI) {
        super(IRI);
    }

    public ComplexLogicEvent(LogicOperator logicOperator, LinkedList<Event> events, String IRI) {
        super(events, IRI);
        this.logicOperator = logicOperator;
    }


    public LogicOperator getLogicOperator() {
        return logicOperator;
    }

    public void setLogicOperator(LogicOperator temporalOperator) {
        this.logicOperator = temporalOperator;
    }

    @Override
    public String interpret(InterpreterContext context) throws InterpreterException {
        switch (context) {
            case ESPER: {
                if (logicOperator.getOperator().equals(LogicOperatorEnum.Conjunction) || logicOperator.getOperator().equals(LogicOperatorEnum.Disjunction)) {
                    Event head = events.pollFirst();
                    String logicalEvent = "(" + head.interpret(context) + ")";
                    head = events.pollFirst();
                    while (head != null) {
                        logicalEvent += logicOperator.interpret(context);
                        logicalEvent += "(" + head.interpret(context) + ")";
                        head = events.pollFirst();
                    }
                    return logicalEvent;
                }
                if (logicOperator.getOperator().equals(LogicOperatorEnum.Negation)) {
                    return logicOperator.interpret(context) + "(" + events.pollFirst().interpret(context) + ")";
                }
                throw new InterpreterException("wrong logical operator");
            }
            default: {
                if (logicOperator.getOperator().equals(LogicOperatorEnum.Conjunction) || logicOperator.getOperator().equals(LogicOperatorEnum.Disjunction)) {
                    Event head = events.pollFirst();
                    String logicalEvent = "(" + head.interpret(context) + ")";
                    head = events.pollFirst();
                    while (head != null) {
                        logicalEvent += logicOperator.interpret(context);
                        logicalEvent += "(" + head.interpret(context) + ")";
                        head = events.pollFirst();
                    }
                    return logicalEvent;
                }
                if (logicOperator.getOperator().equals(LogicOperatorEnum.Negation)) {
                    return logicOperator.interpret(context) + " (" + events.pollFirst().interpret(context) + ")";
                }
                throw new InterpreterException("wrong logical operator");
            }
        }
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        Map<String, String> map = new HashMap<>();
        switch (context) {
            case ESPER: {
                map.put("complex logical event", interpret(context));
                return map;
            }
            default: {
                map.put("complex logical event", interpret(context));
                return map;
            }
        }
    }
}
