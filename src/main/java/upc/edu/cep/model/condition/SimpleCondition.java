package upc.edu.cep.model.condition;

/**
 * Created by osboxes on 17/04/17.
 */
public class SimpleCondition extends NonTemporalCondition {

    private ConditionOperator operator;
    private Operand operand1;
    private Operand operand2;

    public SimpleCondition() {
    }

    public SimpleCondition(ConditionOperator operator, Operand operand1, Operand operand2) {
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public ConditionOperator getOperator() {
        return operator;
    }

    public void setOperator(ConditionOperator operator) {
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
