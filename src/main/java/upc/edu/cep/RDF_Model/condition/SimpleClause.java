package upc.edu.cep.RDF_Model.condition;

import upc.edu.cep.RDF_Model.Operators.ComparasionOperator;

/**
 * Created by osboxes on 17/04/17.
 */
public class SimpleClause extends Condition {

    private ComparasionOperator operator;
    private Operand operand1;
    private Operand operand2;

    public SimpleClause() {
        super();
    }

    public SimpleClause(ComparasionOperator operator, Operand operand1, Operand operand2) {
        super();
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public SimpleClause(String IRI) {
        super(IRI);
    }

    public SimpleClause(ComparasionOperator operator, Operand operand1, Operand operand2, String IRI) {
        super(IRI);
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public ComparasionOperator getOperator() {
        return operator;
    }

    public void setOperator(ComparasionOperator operator) {
        this.operator = operator;
    }

    public Operand getOperand1() {
        return operand1;
    }

    public void setOperand1(Operand operand1) {
        this.operand1 = operand1;
    }

    public Operand getOperand2() {
        return operand2;
    }

    public void setOperand2(Operand operand2) {
        this.operand2 = operand2;
    }
}
