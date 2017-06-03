package upc.edu.cep.manager;

import upc.edu.cep.Interpreter.InterpreterContext;
import upc.edu.cep.Interpreter.InterpreterException;
import upc.edu.cep.RDF_Model.Rule;
import upc.edu.cep.RDF_Model.condition.Operand;
import upc.edu.cep.RDF_Model.event.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by osboxes on 31/05/17.
 */
public class FlumeSink {
    private FlumeChannel flumeChannel;
    private Rule rule;
    private String sinkName;
    private String agentName;
    private boolean restart;

    public FlumeSink() {
    }

    public FlumeChannel getFlumeChannel() {
        return flumeChannel;
    }

    public void setFlumeChannel(FlumeChannel flumeChannel) {
        this.flumeChannel = flumeChannel;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public String getSinkName() {
        return sinkName;
    }

    public void setSinkName(String sinkName) {
        this.sinkName = sinkName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public boolean isRestart() {
        return restart;
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    public String interpret(String deletedRules) throws InterpreterException {
        String prefix = agentName + ManagerConstants.DOT + ManagerConstants.SINKS_PREFIX + sinkName + ManagerConstants.DOT;
        String result = "";
        if (deletedRules != "" || deletedRules != null) {
            result = String.join("\n", result
                    , prefix + ManagerConstants.CEP_SINK_DELETED_RULES + " = " + deletedRules
            );
        }

        result = String.join("\n", result
                , prefix + ManagerConstants.CEP_SINK_RULE_ID + " = " + rule.getIRI()
                , prefix + ManagerConstants.CEP_SINK_EXPRESSION + " = " + rule.interpret(InterpreterContext.ESPER)
                , prefix + ManagerConstants.TYPE + " = upc.edu.cep.flume.sinks.CEPSink"
                , prefix + ManagerConstants.CEP_SINK_RESTART + " = " + restart
                , prefix + ManagerConstants.CEP_SINK_CHANNEL + " = " + flumeChannel.getChannelName()
        );

        List<SimpleEvent> simpleEvents = new ArrayList<>();
        Queue<Event> eventQueue = new ArrayDeque<>();
        eventQueue.add(rule.getEvent());
        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.poll();
            if (event.getClass().equals(SimpleEvent.class)) {
                simpleEvents.add((SimpleEvent) event);
            } else if (!event.getClass().equals(TimeEvent.class)) {
                for (Event e : ((ComplexEvent) event).getEvents()) {
                    eventQueue.add(e);
                }
            }
        }

        String events = "";
        String actions = "";
        for (SimpleEvent e : simpleEvents) {
            events += " " + e.getAtomicEvent().getEventName();
        }
        for (Operand o : rule.getAction().getActionAttributes()) {
            actions += " " + o.interpret(InterpreterContext.ESPER);
        }
        result = String.join("\n", result
                , prefix + ManagerConstants.CEP_SINK_EVENT_NAMES + " = " + events
                , prefix + ManagerConstants.CEP_SINK_ACTIONS + " = " + actions
        );
        for (SimpleEvent e : simpleEvents) {
            String eventAtts = "";
            for (Attribute attribute : e.getAtomicEvent().getAttributes()) {
                eventAtts += " " + attribute.getName();
            }
            eventAtts = eventAtts.trim();
            result = String.join("\n", result
                    , prefix + e.getAtomicEvent().getEventName() + ManagerConstants.DOT + ManagerConstants.CEP_SINK_EVENT_ATTRIBUTES + " = " + eventAtts
            );
            for (Attribute attribute : e.getAtomicEvent().getAttributes()) {
                result = String.join("\n", result
                        , prefix + e.getAtomicEvent().getEventName() + ManagerConstants.DOT + attribute.getName() + ManagerConstants.DOT + ManagerConstants.TYPE + " = " + attribute.getAttributeType().toString());
            }
        }

        return result;
    }
}

//        remote_agent.sinks.CEPSink.Event1.attributes = mylog yourlog
//        remote_agent.sinks.CEPSink.Event1.mylog.type=string
//        remote_agent.sinks.CEPSink.Event1.yourlog.type=string
