package upc.edu.cep.Interpreter;

import java.util.Map;

/**
 * Created by osboxes on 24/05/17.
 */
public interface Interpreter {

    public String interpret(InterpreterContext context) throws InterpreterException;

    public Map<String, String> interpretToMap(InterpreterContext context) throws InterpreterException;
}
