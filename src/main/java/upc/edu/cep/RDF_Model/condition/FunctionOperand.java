package upc.edu.cep.RDF_Model.condition;

import java.util.List;

/**
 * Created by osboxes on 18/04/17.
 */
public class FunctionOperand extends Operand {

    private String functionName;
    private String functionURL;
    private Method functionMethod;
    ;
    private List<FunctionParameter> parameters;

    public FunctionOperand(String functionName, List<FunctionParameter> parameters, String functionURL, Method functionMethod) {
        super();
        this.functionName = functionName;
        this.functionURL = functionURL;
        this.functionMethod = functionMethod;
        this.parameters = parameters;
    }


    public FunctionOperand() {
        super();
    }

    public FunctionOperand(String functionName, List<FunctionParameter> parameters, String functionURL, Method functionMethod, String IRI) {
        super(IRI);
        this.functionName = functionName;
        this.functionURL = functionURL;
        this.functionMethod = functionMethod;
        this.parameters = parameters;
    }


    public FunctionOperand(String IRI) {
        super(IRI);
    }

    public List<FunctionParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<FunctionParameter> innerOperand) {
        this.parameters = parameters;
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

    public enum Method {GET, POST}
}
