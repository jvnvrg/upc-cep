package upc.edu.cep.RDF_Model.Operators;

import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;

import java.util.Map;

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

    @Override
    public String interpret(InterpreterContext context) throws InterpreterException {
        switch (context) {
            case ESPER: {
                return "where timer:within(" + offset + " " + timeUnit + ")";
            }
            default: {
                return "where timer:within(" + offset + " " + timeUnit + ")";
            }
        }
    }

    @Override
    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException {
        throw new InterpreterException("not supported");
    }
}
