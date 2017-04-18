package upc.edu.cep.model.condition;

/**
 * Created by osboxes on 18/04/17.
 */
public class LiteralOperand extends Operand {
    private String type;
    private String value;

    public LiteralOperand(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public LiteralOperand() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
