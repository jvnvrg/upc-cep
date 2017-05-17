package upc.edu.cep.RDF_Model.Operators;


/**
 * Created by osboxes on 17/04/17.
 */
public class TemportalWindow extends TemporalOperator {

    WindowType windowType;
    int within;
    TimeUnit timeUnit;

    public TemportalWindow() {
    }

    public TemportalWindow(WindowType windowType, int within, TimeUnit timeUnit) {
        this.windowType = windowType;
        this.within = within;
        this.timeUnit = timeUnit;
    }

    public WindowType getWindowType() {
        return windowType;
    }

    public void setWindowType(WindowType windowType) {
        this.windowType = windowType;
    }

    public int getWithin() {
        return within;
    }

    public void setWithin(int within) {
        this.within = within;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public enum WindowType {
        TUMBLING_WINDOW,
        SLIDING_WINDOW
    }
}