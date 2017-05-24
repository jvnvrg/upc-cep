package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.RDF_Model.condition.Operand;

/**
 * Created by osboxes on 14/05/17.
 */
public class Attribute extends Operand {

    private String name;
    private AttributeType attributeType;

    public Attribute() {
        super();
    }

    public Attribute(String name, AttributeType attributeType) {
        super();
        this.name = name;
    }

    public Attribute(String IRI) {
        super(IRI);
    }

    public Attribute(String name, AttributeType attributeType, String IRI) {
        super(IRI);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
