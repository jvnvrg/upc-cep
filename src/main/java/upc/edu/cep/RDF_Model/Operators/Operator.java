package upc.edu.cep.RDF_Model.Operators;

/**
 * Created by osboxes on 17/05/17.
 */
public abstract class Operator {
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
