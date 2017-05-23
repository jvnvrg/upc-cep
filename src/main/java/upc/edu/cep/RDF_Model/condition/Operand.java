package upc.edu.cep.RDF_Model.condition;

/**
 * Created by osboxes on 18/04/17.
 */
public class Operand {

    protected String IRI;

    public Operand(String IRI) {
        this.IRI = IRI;
    }

    public Operand() {
    }

    public String getIRI() {
        return IRI;
    }

    public void setIRI(String IRI) {
        this.IRI = IRI;
    }
}
