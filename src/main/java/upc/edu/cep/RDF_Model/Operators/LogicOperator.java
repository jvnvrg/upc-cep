package upc.edu.cep.RDF_Model.Operators;


/**
 * Created by osboxes on 15/05/17.
 */
public class LogicOperator extends Operator {

    private LogicOperatorEnum operator;

    public LogicOperator(LogicOperatorEnum operator) {
        super();
        this.operator = operator;
    }

    public LogicOperator(LogicOperatorEnum operator, String IRI) {
        super(IRI);
        this.operator = operator;
    }

    public LogicOperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(LogicOperatorEnum operator) {
        this.operator = operator;
    }

}
