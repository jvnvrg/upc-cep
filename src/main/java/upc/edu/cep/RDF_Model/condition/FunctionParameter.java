package upc.edu.cep.RDF_Model.condition;

/**
 * Created by osboxes on 15/05/17.
 */
public class FunctionParameter {
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
}
