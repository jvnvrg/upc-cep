package upc.edu.cep.model.condition;

/**
 * Created by osboxes on 18/04/17.
 */
public class FunctionOperand extends Operand {

    private String functionName;
    private String functionURL;
    public enum Method {GET, POST};
    private Method functionMethod;
    private Operand innerOperand;


    public FunctionOperand(String functionName, Operand innerOperand, String functionURL, Method functionMethod) {
        this.functionName = functionName;
        this.functionURL = functionURL;
        this.functionMethod = functionMethod;
        this.innerOperand = innerOperand;
    }

    public FunctionOperand() {
    }

    public Operand getInnerOperand() {
        return innerOperand;
    }

    public void setInnerOperand(Operand innerOperand) {
        this.innerOperand = innerOperand;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
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
