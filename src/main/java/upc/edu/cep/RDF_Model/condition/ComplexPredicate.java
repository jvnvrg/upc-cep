package upc.edu.cep.RDF_Model.condition;

import upc.edu.cep.RDF_Model.Operators.LogicOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osboxes on 17/04/17.
 */
public abstract class ComplexPredicate {

    List<Condition> conditions;
    LogicOperator operator;

    public ComplexPredicate() {
        conditions = new ArrayList<>();
    }

    public ComplexPredicate(List<Condition> conditions, LogicOperator operator) {
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
