package upc.edu.cep.RDF_Model.Operators;

/**
 * Created by osboxes on 18/05/17.
 */
public class OperatorWithImplicitTime extends TemporalOperator {
    public OperatorWithImplicitTime(TemporalOperatorEnum operator) {
        super(operator);
    }

    public OperatorWithImplicitTime() {
    }

    public OperatorWithImplicitTime(TemporalOperatorEnum operator, String IRI) {
        super(operator, IRI);
    }

    public OperatorWithImplicitTime(String IRI) {
        super(IRI);
    }
}
