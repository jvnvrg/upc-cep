package upc.edu.cep.kafka.producers;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by osboxes on 04/05/17.
 */
public class Event2 implements Serializable {
    String name;
    Map<String,String> values;

    public Event2(String name, Map values) {
        this.name = name;
        this.values = values;
    }

    public Event2() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getValues() {
        return values;
    }

    public void setValues(Map values) {
        this.values = values;
    }
}
