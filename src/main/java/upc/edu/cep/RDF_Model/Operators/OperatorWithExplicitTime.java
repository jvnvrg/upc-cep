package upc.edu.cep.RDF_Model.Operators;

/**
 * Created by osboxes on 18/05/17.
 */
public class OperatorWithExplicitTime extends TemporalOperator {

    TimeUnit timeUnit;
    private int offset;

    public OperatorWithExplicitTime(int offset, TimeUnit timeUnit, TemporalOperatorEnum operator) {
        super(operator);
        this.offset = offset;
        this.timeUnit = timeUnit;
    }

    public OperatorWithExplicitTime() {
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
