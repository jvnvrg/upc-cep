package upc.edu.cep.model.condition;

import com.espertech.esper.epl.join.hint.ExcludePlanFilterOperatorType;

/**
 * Created by osboxes on 18/04/17.
 */
public class ConditionOperator {

    public static enum Operators {
        EQ,
        NE,
        GT,
        GE,
        LE,
        LT,
    }

    private Operators operator;

    public Operators getOperator() {
        return operator;
    }

    public void setOperator(Operators operator) {
        this.operator = operator;
    }

    public ConditionOperator(Operators operator) {
        this.operator = operator;
    }
}





