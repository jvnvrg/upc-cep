package upc.edu.cep.kafka.producers;

/**
 * Created by osboxes on 04/04/17.
 */

import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;



public class JsonTest {

    public static void main(String[] args) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Event2 event2 = new Event2();
        //   event2.setName("dodo");
        Map<String,String> aaa = new HashMap();
        aaa.put("t1","v1");
        aaa.put("t2","v2");
//        event2.setValues(aaa);


        String eventString = objectMapper.writeValueAsString(event2);

        Gson gson = new Gson();
        Map map = gson.fromJson(eventString,HashMap.class);

//        JsonObject jsonObject = new JsonObject();
//
//        String eventName = jsonObject.get("name").getAsString();
        System.out.println(eventString);

    }

}
