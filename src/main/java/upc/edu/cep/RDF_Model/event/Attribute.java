package upc.edu.cep.RDF_Model.event;

import upc.edu.cep.model.condition.Operand;

/**
 * Created by osboxes on 14/05/17.
 */
public class Attribute extends Operand {

    private String name;
    private AttributeType attributeType;

    public Attribute() {
    }

    public Attribute(String name, AttributeType attributeType) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
