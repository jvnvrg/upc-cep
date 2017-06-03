package upc.edu.cep.manager;

import upc.edu.cep.RDF_Model.condition.LiteralOperand;
import upc.edu.cep.RDF_Model.condition.SimpleClause;
import upc.edu.cep.RDF_Model.event.*;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by osboxes on 31/05/17.
 */
public class FlumeSource {

    AtomicEvent event;
    //    AtomicEvent atomicEvent;
    private String zookeeperConnect;
    private String streamType;
    private String agentName;
    private String sourceName;

    public FlumeSource() {
    }

    public String getZookeeperConnect() {
        return zookeeperConnect;
    }

    public void setZookeeperConnect(String zookeeperConnect) {
        this.zookeeperConnect = zookeeperConnect;
    }

    public String getStreamType() {
        return streamType;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public AtomicEvent getEvent() {
        return event;
    }

    public void setEvent(AtomicEvent event) {
        this.event = event;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String interpret(Map<FlumeSource, List<FlumeSink>> sourceSinksMap) {

        String prefix = agentName + ManagerConstants.DOT + ManagerConstants.SOURCES + ManagerConstants.DOT + sourceName + ManagerConstants.DOT;
        String eventAtts = "";
        for (Attribute attribute : event.getAttributes()) {
            eventAtts += " " + attribute.getName();
        }
        eventAtts = eventAtts.trim();
        String result = String.join("\n"
                , prefix + ManagerConstants.TYPE + " = upc.edu.cep.flume.sources.CEPKafkaSource"
                , prefix + ManagerConstants.SOURCE_EVENT_TYPE + " = json"
                , prefix + ManagerConstants.SOURCE_EVENT_NAME + " = " + event.getEventName()
                , prefix + ManagerConstants.SOURCE_ATTRIBUTES + " = " + eventAtts
        );
        for (Attribute attribute : event.getAttributes()) {
            result = String.join("\n", result
                    , prefix + attribute.getName() + ManagerConstants.DOT + ManagerConstants.TYPE + " = " + attribute.getAttributeType().toString());
        }
        result = String.join("\n", result
                , prefix + ManagerConstants.SOURCE_ZOOKEEPER_CONNECT + " = " + zookeeperConnect
                , prefix + ManagerConstants.SOURCE_TOPIC + " = " + event.getTopicName()
                , prefix + ManagerConstants.SOURCE_BATCH_SIZE + " = 100"
                , prefix + ManagerConstants.INTERCEPTORS + " = " + "TimestampInterceptor HostInterceptor EventNameInterceptor"
                , prefix + ManagerConstants.EVENTNAME_INTERCEPTOR_EVENTNAME + " = " + event.getEventName()
                , prefix + ManagerConstants.EVENTNAME_INTERCEPTOR_TYPE + " = " + ManagerConstants.EVENTNAME_INTERCEPTOR_TYPE_INSTANCE
                , prefix + ManagerConstants.TIMESTAMP_INTERCEPTOR_TYPE + " = " + ManagerConstants.TIMESTAMP_INTERCEPTOR_TYPE_INSTANCE
                , prefix + ManagerConstants.HOST_INTERCEPTOR_TYPE + " = " + ManagerConstants.HOST_INTERCEPTOR_TYPE_INSTANCE
                , prefix + ManagerConstants.HOST_INTERCEPTOR_PRESERVEEXISTING + " = " + ManagerConstants.HOST_INTERCEPTOR_PRESERVEEXISTING_INSTANCE
                , prefix + ManagerConstants.HOST_INTERCEPTOR_HOSTHEADER + " = " + ManagerConstants.HOST_INTERCEPTOR_HOSTHEADER_INSTANCE
        );

        List<FlumeSink> flumeSinks = sourceSinksMap.get(this);

        String channels = "";
        for (FlumeSink flumeSink : flumeSinks) {
            channels += " " + flumeSink.getFlumeChannel().getChannelName();
        }

        result = String.join("\n", result
                , prefix + ManagerConstants.CHANNELS + " = " + channels
        );
        boolean filter = false;
        for (FlumeSink flumeSink : flumeSinks) {
            Queue<Event> eventQueue = new ArrayDeque<>();
            eventQueue.add(flumeSink.getRule().getEvent());
            while (!eventQueue.isEmpty()) {
                Event head = eventQueue.poll();
                if (head.getClass().equals(SimpleEvent.class)) {
                    SimpleEvent e = (SimpleEvent) head;
                    if (e.getAtomicEvent().getIRI().equals(event.getIRI())) {
                        if (!e.getFilters().isEmpty()) {
                            for (SimpleClause sc : e.getFilters()) {
                                LiteralOperand lo;
                                Attribute a;
                                if (sc.getOperand1().getClass().equals(LiteralOperand.class)) {
                                    lo = (LiteralOperand) sc.getOperand1();
                                    a = (Attribute) sc.getOperand2();
                                } else {
                                    lo = (LiteralOperand) sc.getOperand2();
                                    a = (Attribute) sc.getOperand1();
                                }
                                filter = true;
                                result = String.join("\n", result
                                        , prefix + ManagerConstants.SELECTOR_PREFIX + flumeSink.getFlumeChannel().getChannelName() + " = " + a.getName()
                                        , prefix + ManagerConstants.SELECTOR_PREFIX + flumeSink.getFlumeChannel().getChannelName() + ManagerConstants.DOT + a.getName() + ManagerConstants.DOT + sc.getOperator().toString() + " = " + lo.getValue()
                                );
                            }
                        }
                    }
                } else if (!event.getClass().equals(TimeEvent.class)) {
                    for (Event e : ((ComplexEvent) head).getEvents()) {
                        eventQueue.add(e);
                    }
                }
            }
        }
        if (filter) {
            result = String.join("\n", result
                    , prefix + ManagerConstants.SELECTOR_TYPE + " = " + "upc.edu.cep.flume.selectors.CEPFilterSelector"
                    , prefix + ManagerConstants.SELECTOR_CHANNELS + " = " + channels
                    , prefix + ManagerConstants.SELECTOR_PREFIX + ManagerConstants.SOURCE_EVENT_NAME + " = " + event.getEventName()
                    , prefix + ManagerConstants.SELECTOR_PREFIX + ManagerConstants.SOURCE_ATTRIBUTES + " = " + eventAtts
            );
            for (Attribute attribute : event.getAttributes()) {
                result = String.join("\n", result
                        , prefix + ManagerConstants.SELECTOR_PREFIX + attribute.getName() + ManagerConstants.DOT + ManagerConstants.TYPE + " = " + attribute.getAttributeType().toString());
            }
        }

        return result;
    }
}