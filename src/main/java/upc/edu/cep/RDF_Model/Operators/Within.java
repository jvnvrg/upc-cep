package upc.edu.cep.RDF_Model.Operators;

/**
 * Created by osboxes on 18/05/17.
 */
public class Within extends TemporalOperator {

    TimeUnit timeUnit;
    private int offset;

    public Within(int offset, TimeUnit timeUnitr) {
        super(TemporalOperatorEnum.Within);
        this.offset = offset;
        this.timeUnit = timeUnit;
    }

    public Within() {
        super(TemporalOperatorEnum.Within);
    }

    public Within(int offset, TimeUnit timeUnit, String IRI) {
        super(TemporalOperatorEnum.Within, IRI);
        this.offset = offset;
        this.timeUnit = timeUnit;
    }

    public Within(String IRI) {
        super(IRI);
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
