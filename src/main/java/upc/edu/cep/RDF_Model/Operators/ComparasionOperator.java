package upc.edu.cep.RDF_Model.Operators;


/**
 * Created by osboxes on 18/04/17.
 */
public class ComparasionOperator extends Operator {

    private ComparasionOperatorEnum operator;

    public ComparasionOperator(ComparasionOperatorEnum operator) {
        this.operator = operator;
    }

    public ComparasionOperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(ComparasionOperatorEnum operator) {
        this.operator = operator;
    }
}





