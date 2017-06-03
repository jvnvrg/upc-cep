package upc.edu.cep.manager;

import upc.edu.cep.RDF_Model.Rule;
import upc.edu.cep.RDF_Model.event.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by osboxes on 30/05/17.
 */
public class Manager {

    private Map<FlumeSource, List<FlumeSink>> sourceSinksMap = new HashMap<>();

    public String CreateConfiguration(String agentName, List<AtomicEvent> events, List<Rule> rules, String zookeeperConnect, String streamType, boolean restart, String deletedRules) throws Exception {
//        BASE64Encoder encoder = new BASE64Encoder();
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(someString.getBytes());
//        byte[] bMac = md.digest();
//        String anotherString = encoder.encodeBuffer(bMac);

        List<FlumeSink> flumeSinks = new ArrayList<>();
        List<FlumeSource> flumeSources = new ArrayList<>();
        List<FlumeChannel> flumeChannels = new ArrayList<>();

        for (Rule rule : rules) {
            String iri = rule.getIRI();
            FlumeChannel flumeChannel = new FlumeChannel();
            FlumeSink flumeSink = new FlumeSink();
            flumeChannel.setAgentName(agentName);
            flumeChannel.setChannelName("Channel" + iri);
            flumeSink.setSinkName("Sink" + iri);
            flumeSink.setRule(rule);
            flumeSink.setFlumeChannel(flumeChannel);
            flumeSink.setAgentName(agentName);
            flumeSink.setRestart(restart);
            flumeChannel.setFlumeSink(flumeSink);
            flumeChannels.add(flumeChannel);
            flumeSinks.add(flumeSink);
        }

        for (AtomicEvent event : events) {
            FlumeSource flumeSource = new FlumeSource();
            flumeSource.setEvent(event);
            flumeSource.setStreamType(streamType);
            flumeSource.setZookeeperConnect(zookeeperConnect);
            flumeSource.setSourceName("Source" + event.getIRI());
            flumeSource.setAgentName(agentName);
            flumeSources.add(flumeSource);
            sourceSinksMap.put(flumeSource, new ArrayList<>());
        }
        for (FlumeSink flumeSink : flumeSinks) {
            fillMap(flumeSink, flumeSink.getRule().getEvent());
        }

        String sources = "", channels = "", sinks = "";
        for (FlumeSink sink : flumeSinks)
            sinks += " " + sink.getSinkName();
        for (FlumeSource source : flumeSources)
            sources += " " + source.getSourceName();
        for (FlumeChannel channel : flumeChannels)
            channels += " " + channel.getChannelName();
        String result = String.join("\n"
                , agentName + ManagerConstants.DOT + ManagerConstants.SOURCES + " = " + sources
                , agentName + ManagerConstants.DOT + ManagerConstants.CHANNELS + " = " + channels
                , agentName + ManagerConstants.DOT + ManagerConstants.SINKS + " = " + sinks
        );

        result = String.join("\n", result, "\n", "#### Sources ####", "\n");
        for (FlumeSource source : flumeSources) {
            result = String.join("\n", result, source.interpret(sourceSinksMap), "\n");
        }
        result = String.join("\n", result, "#### Channels ####", "\n");
        for (FlumeChannel channel : flumeChannels) {
            result = String.join("\n", result, channel.interpret(), "\n");
        }
        result = String.join("\n", result, "#### Sinks ####");
        for (FlumeSink sink : flumeSinks) {
            result = String.join("\n", result, sink.interpret(deletedRules), "\n");
        }

        return result;
    }

    private void fillMap(FlumeSink sink, Event event) {
        if (event.getClass().equals(SimpleEvent.class)) {
            for (FlumeSource flumeSource : sourceSinksMap.keySet()) {
                if (flumeSource.getEvent().equals(((SimpleEvent) event).getAtomicEvent())) {
                    if (!sourceSinksMap.get(flumeSource).contains(sink)) {
                        sourceSinksMap.get(flumeSource).add(sink);
                    }
                }
            }
        } else if (!event.getClass().equals(TimeEvent.class)) {
            for (Event e : ((ComplexEvent) event).getEvents()) {
                fillMap(sink, e);
            }
        }
    }
}
