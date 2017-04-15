package upc.edu.cep.model;

import java.util.Map;

/**
 * Created by osboxes on 14/04/17.
 */
public class SimpleEvent extends Event{
    private Map<String, Class> attributes;

    public void addAttribute(String attName, Class attType) {
        attributes.put(attName, attType);
    }

    public Map<String, Class> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Class> attributes) {
        this.attributes = attributes;
    }
}
