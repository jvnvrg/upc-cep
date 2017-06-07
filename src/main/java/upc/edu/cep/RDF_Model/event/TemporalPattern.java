package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;
import upc.edu.cep.RDF_Model.Operators.TemporalOperator;
import upc.edu.cep.RDF_Model.Operators.TemporalOperatorEnum;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by osboxes on 15/05/17.
 */
public class TemporalPattern extends Pattern {

    protected TemporalOperator temporalOperator;

    public TemporalPattern() {
        super();
    }

    public TemporalPattern(TemporalOperator temporalOperator, LinkedList<CEPElement> CEPElements) {
        super(CEPElements);
        this.temporalOperator = temporalOperator;
    }

    public TemporalPattern(String IRI) {
        super(IRI);
    }

    public TemporalPattern(TemporalOperator temporalOperator, LinkedList<CEPElement> CEPElements, String IRI) {
        super(CEPElements, IRI);
        this.temporalOperator = temporalOperator;
    }

    public TemporalOperator getTemporalOperator() {
        return temporalOperator;
    }

    public void setTemporalOperator(TemporalOperator temporalOperator) {
        this.temporalOperator = temporalOperator;
    }

    @Override
    public String interpret(InterpreterContext context) throws InterpreterException {
        LinkedList<CEPElement> copy = new LinkedList<>(CEPElements);
        switch (context) {
            case ESPER: {
                if (temporalOperator.getOperator().equals(TemporalOperatorEnum.Sequence)) {
                    CEPElement head = copy.pollFirst();
                    String logicalEvent = head.interpret(context);
                    head = copy.pollFirst();
                    while (head != null) {
                        logicalEvent += temporalOperator.interpret(context);
                        logicalEvent += "(" + head.interpret(context) + ")";
                        head = copy.pollFirst();
                    }
                    return logicalEvent;
                }
                if (temporalOperator.getOperator().equals(TemporalOperatorEnum.Within)) {
                    return copy.pollFirst().interpret(context) + " " + temporalOperator.interpret(context);
                }
                throw new InterpreterException("wrong temporal operator");
            }
            default: {
                if (temporalOperator.getOperator().equals(TemporalOperatorEnum.Sequence)) {
                    CEPElement head = copy.pollFirst();
                    String logicalEvent = "(" + head.interpret(context) + ")";
                    head = copy.pollFirst();
                    while (head != null) {
                        logicalEvent += temporalOperator.interpret(context);
                        logicalEvent += "(" + head.interpret(context) + ")";
                        head = copy.pollFirst();
                    }
                    return logicalEvent;
                }
                if (temporalOperator.getOperator().equals(TemporalOperatorEnum.Within)) {
                    return copy.pollFirst().interpret(context) + " " + temporalOperator.interpret(context);
                }
                throw new InterpreterException("wrong temporal operator");
            }
        }
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        Map<String, String> map = new HashMap<>();
        switch (context) {
            case ESPER: {
                map.put("complex temporal event", interpret(context));
                return map;
            }
            default: {
                map.put("complex temporal event", interpret(context));
                return map;
            }
        }
    }
}
