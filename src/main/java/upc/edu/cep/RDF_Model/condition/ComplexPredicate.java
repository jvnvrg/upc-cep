package upc.edu.cep.RDF_Model.condition;

import upc.edu.cep.RDF_Model.Operators.LogicOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osboxes on 17/04/17.
 */
public class ComplexPredicate extends Condition {

    protected List<Condition> conditions;
    protected LogicOperator operator;

    public ComplexPredicate() {
        super();
        conditions = new ArrayList<>();
    }

    public ComplexPredicate(List<Condition> conditions, LogicOperator operator) {
        super();
        this.conditions = conditions;
        this.operator = operator;
    }

    public ComplexPredicate(String IRI) {
        super(IRI);
        conditions = new ArrayList<>();
    }

    public ComplexPredicate(List<Condition> conditions, LogicOperator operator, String IRI) {
        super(IRI);
        this.conditions = conditions;
        this.operator = operator;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public LogicOperator getOperator() {
        return operator;
    }

    public void setOperator(LogicOperator operator) {
        this.operator = operator;
    }
}
