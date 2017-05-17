package upc.edu.cep.RDF_Model.Operators;

/**
 * Created by osboxes on 15/05/17.
 */
public class Within extends TemporalOperator {

    TimeUnit timeUnit;
    private int offset;

    public Within(int offset, TimeUnit timeUnit) {
        this.offset = offset;
        this.timeUnit = timeUnit;
    }

    public Within() {
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
