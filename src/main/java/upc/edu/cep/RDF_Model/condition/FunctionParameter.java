package upc.edu.cep.RDF_Model.condition;

import upc.edu.cep.Interpreter.Interpreter;
import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;

import java.util.Map;

/**
 * Created by osboxes on 15/05/17.
 */
public class FunctionParameter implements Interpreter, Comparable<FunctionParameter> {
    private Operand operand;
    private int order;
    private String IRI;

    public FunctionParameter() {
    }

    public FunctionParameter(Operand operand, int order, String IRI) {
        this.operand = operand;
        this.order = order;
        this.IRI = IRI;
    }

    public Operand getOperand() {
        return operand;
    }

    public void setOperand(Operand operand) {
        this.operand = operand;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getIRI() {
        return IRI;
    }

    public void setIRI(String IRI) {
        this.IRI = IRI;
    }

    @Override
    public String interpret(InterpreterContext context) throws InterpreterException {
        return operand.interpret(context);
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        return operand.interpretToMap(context);
    }

    @Override
    public int compareTo(FunctionParameter o2) {
        return this.order < o2.order ? -1
                : this.order > o2.order ? 1
                : 0;
    }
}
