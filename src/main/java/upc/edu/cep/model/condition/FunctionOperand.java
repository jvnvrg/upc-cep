package upc.edu.cep.model.condition;

/**
 * Created by osboxes on 18/04/17.
 */
public class FunctionOperand extends Operand {

    private String functionName;
    private boolean ouEventAttribute;
    private String eventName;
    private String eventAttributeName;
    private String functionURL;
    public enum Method {GET,POST};
    private Method functionMethod;

    public FunctionOperand(String functionName, boolean ouEventAttribute, String eventName, String eventAttributeName, String functionURL, Method functionMethod) {
        this.functionName = functionName;
        this.ouEventAttribute = ouEventAttribute;
        this.eventName = eventName;
        this.eventAttributeName = eventAttributeName;
        this.functionURL = functionURL;
        this.functionMethod = functionMethod;
    }

    public FunctionOperand() {
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public boolean isOuEventAttribute() {
        return ouEventAttribute;
    }

    public void setOuEventAttribute(boolean ouEventAttribute) {
        this.ouEventAttribute = ouEventAttribute;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventAttributeName() {
        return eventAttributeName;
    }

    public void setEventAttributeName(String eventAttributeName) {
        this.eventAttributeName = eventAttributeName;
    }

    public String getFunctionURL() {
        return functionURL;
    }

    public void setFunctionURL(String functionURL) {
        this.functionURL = functionURL;
    }

    public Method getFunctionMethod() {
        return functionMethod;
    }

    public void setFunctionMethod(Method functionMethod) {
        this.functionMethod = functionMethod;
    }
}
