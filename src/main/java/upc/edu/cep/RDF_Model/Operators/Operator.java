package upc.edu.cep.RDF_Model.Operators;

import upc.edu.cep.Interpreter.Interpreter;

/**
 * Created by osboxes on 17/05/17.
 */
public abstract class Operator implements Interpreter {
    protected String IRI;

    public Operator(String IRI) {
        this.IRI = IRI;
    }

    public Operator() {
    }

    public String getIRI() {
        return IRI;
    }

    public void setIRI(String IRI) {
        this.IRI = IRI;
    }
}
