package upc.edu.cep.model.condition;

/**
 * Created by osboxes on 17/04/17.
 */
public abstract class UnaryComplexCondition extends Condition {

    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
