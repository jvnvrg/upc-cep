package upc.edu.cep.RDF_Model.condition;

/**
 * Created by osboxes on 15/05/17.
 */
public class FunctionParameter {
    private Operand operand;
    private int order;

    public FunctionParameter() {
    }

    public FunctionParameter(Operand operand, int order) {
        this.operand = operand;
        this.order = order;
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
}
