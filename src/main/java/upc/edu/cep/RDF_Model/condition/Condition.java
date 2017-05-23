package upc.edu.cep.RDF_Model.condition;

/**
 * Created by osboxes on 17/04/17.
 */
public abstract class Condition {

    protected String IRI;

    public Condition(String IRI) {
        this.IRI = IRI;
    }

    public Condition() {
    }

    public String getIRI() {
        return IRI;
    }

    public void setIRI(String IRI) {
        this.IRI = IRI;
    }
}
