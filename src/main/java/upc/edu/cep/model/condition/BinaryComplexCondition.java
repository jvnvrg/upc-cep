package upc.edu.cep.model.condition;

/**
 * Created by osboxes on 17/04/17.
 */
public abstract class BinaryComplexCondition extends NonTemporalCondition {

    private Condition condition1;
    private Condition condition2;

    public Condition getCondition1() {
        return condition1;
    }

    public void setCondition1(Condition condition1) {
        this.condition1 = condition1;
    }

    public Condition getCondition2() {
        return condition2;
    }

    public void setCondition2(Condition condition2) {
        this.condition2 = condition2;
    }
}
