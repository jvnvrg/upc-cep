package upc.edu.cep.RDF_Model.window;


import upc.edu.cep.RDF_Model.Operators.TimeUnit;

/**
 * Created by osboxes on 17/04/17.
 */
public class Window {

    String IRI;
    WindowType windowType;
    int within;
    TimeUnit timeUnit;

    public Window() {
    }

    public Window(WindowType windowType, int within, TimeUnit timeUnit, String IRI) {
        this.IRI = IRI;
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

    public String getIRI() {
        return IRI;
    }

    public void setIRI(String IRI) {
        this.IRI = IRI;
    }

    public enum WindowType {
        TUMBLING_WINDOW,
        SLIDING_WINDOW
    }
}
