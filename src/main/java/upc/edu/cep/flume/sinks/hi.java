package upc.edu.cep.flume.sinks;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * Created by osboxes on 12/05/17.
 */
public class hi {


    private static EPServiceProvider epService;

    private static EPStatement statement;

    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration();
        //config.addEventType("com.edu.cep.events.LogEvent",LogEvent.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(config);

        //epService.getEPAdministrator().;

    }
}
