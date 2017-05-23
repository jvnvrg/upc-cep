package upc.edu.cep.RDF_Model.condition;

import upc.edu.cep.RDF_Model.event.AttributeType;

/**
 * Created by osboxes on 18/04/17.
 */
public class LiteralOperand extends Operand {

    private AttributeType type;
    private String value;

    public LiteralOperand(AttributeType type, String value) {
        super();
        this.type = type;
        this.value = value;
    }

    public LiteralOperand() {
        super();
    }

    public LiteralOperand(AttributeType type, String value, String IRI) {
        super(IRI);
        this.type = type;
        this.value = value;
    }

    public LiteralOperand(String IRI) {
        super(IRI);
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
