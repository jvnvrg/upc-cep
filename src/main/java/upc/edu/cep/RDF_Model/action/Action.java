package upc.edu.cep.RDF_Model.action;

import upc.edu.cep.RDF_Model.condition.Operand;
import upc.edu.cep.RDF_Model.event.SimpleEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by osboxes on 17/04/17.
 */
public class Action {

    private SimpleEvent associatedEvent;
    private String assocociatedEventTopic;
    private List<Operand> actionAttributes;
    private ActionType actionType;
    private String serviceURL;
    private List<String> topicNames;

    public Action(SimpleEvent associatedEvent, String associatedEventTopic, List<Operand> actionAttributes, ActionType actionType, String serviceURL, List<String> topicNames) {
        this.associatedEvent = associatedEvent;
        this.assocociatedEventTopic = associatedEventTopic;
        this.actionAttributes = actionAttributes;
        this.actionType = actionType;
        this.serviceURL = serviceURL;
        this.topicNames = topicNames;
    }

    public Action() {
        this.actionAttributes = new ArrayList<>();
        this.topicNames = new ArrayList<String>();
    }

    public SimpleEvent getAssociatedEvent() {
        return associatedEvent;
    }

    public void setAssociatedEvent(SimpleEvent associatedEvent) {
        this.associatedEvent = associatedEvent;
    }

    public String getAssocociatedEventTopic() {
        return assocociatedEventTopic;
    }

    public void setAssocociatedEventTopic(String assocociatedEventTopic) {
        this.assocociatedEventTopic = assocociatedEventTopic;
    }

    public List<Operand> getActionAttributes() {
        return actionAttributes;
    }

    public void setActionAttributes(List<Operand> actionAttributes) {
        this.actionAttributes = actionAttributes;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getServiceURL() {
        return serviceURL;
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public List<String> getTopicNames() {
        return topicNames;
    }

    public void setTopicNames(String[] topicNames) {
        this.topicNames = new ArrayList<String>();
        Collections.addAll(this.topicNames, topicNames);
    }

    public void setTopicNames(List<String> topicNames) {
        this.topicNames = topicNames;
    }

    public void addTopics(String[] topicNames) {
        //this.topicNames= new ArrayList<String>();
        Collections.addAll(this.topicNames, topicNames);
    }

    public void addTopic(String topicName) {
        //this.topicNames= new ArrayList<String>();
        Collections.addAll(this.topicNames, topicName);
    }

    public enum ActionType {
        GET,
        POST,
        KAFKA,
        GET_AND_KAFKA,
        POST_AND_KAFKA
    }
}
