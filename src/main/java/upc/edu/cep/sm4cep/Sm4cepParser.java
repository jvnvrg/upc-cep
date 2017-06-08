package upc.edu.cep.sm4cep;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;

import upc.edu.cep.RDF_Model.Operators.TimeUnit;
import upc.edu.cep.RDF_Model.Rule;
import upc.edu.cep.RDF_Model.condition.SimpleClause;
import upc.edu.cep.RDF_Model.event.Attribute;
import upc.edu.cep.RDF_Model.event.AttributeType;
import upc.edu.cep.RDF_Model.event.CEPElement;
import upc.edu.cep.RDF_Model.event.Event;
import upc.edu.cep.RDF_Model.event.EventSchema;
import upc.edu.cep.RDF_Model.window.Window;
import upc.edu.cep.RDF_Model.window.WindowType;
import upc.edu.cep.RDF_Model.window.WindowUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Sm4cepParser {

    private String endpoint = "http://localhost:8890/sparql";
    private HashMap<String, EventSchema> eventSchemata = new HashMap<String, EventSchema> ();
    private HashMap<String, Attribute> eventAttributes = new HashMap<String, Attribute> ();

    public Sm4cepParser() {

    }

    public Sm4cepParser(String endpoint) {
        this.endpoint = endpoint;
    }

    public static void main(String[] args) {

        Sm4cepParser p1 = new Sm4cepParser();

        Scanner scanner = new Scanner(System.in);

        //JenaSystem.DEBUG_INIT = false;
        //ARQ.setExecutionLogging(Explain.InfoLevel.NONE) ;

        while (true) {
            System.out.println("Enter the rule IRI:");
            String ruleIRI = scanner.nextLine();
            if (ruleIRI.equalsIgnoreCase("exit")) break;

            try {
                //p1.getRule(ruleIRI);

                //p1.getWindow(ruleIRI);

				/*
                ruleIRI = p1.formatIRI(ruleIRI);
				//System.out.println(p1.stringWindow(p1.getWindow(ruleIRI)));
				if (p1.getRule(ruleIRI).getWindow() != null)
					System.out.println(p1.stringWindow(p1.getRule(ruleIRI).getWindow()));
				else
					System.out.println("Rule has no window!");
				*/
                String eventIRI = p1.formatIRI(ruleIRI);
                /////System.out.println(p1.stringSimpleEvent(p1.getSimpleEvent(eventIRI)));

            } catch (Exception e) {
                System.out.println("Error:\n" + e);
            }
        }
        System.out.println("Adeu!");

    }

    // for a given rule IRI get the rule with all its elements 
    public Rule getRule(String ruleIRI) {
        Rule rule = new Rule();
        rule.setIRI(ruleIRI);

        try {
            Window window = this.getWindow(ruleIRI);
            rule.setWindow(window);
        } catch (WindowException we) {
            System.out.println("The rule has the following window exception: \n" + we);
        }


        return rule;
    }

    public Window getWindow(String ruleIRI) throws WindowException {
        Window window = new Window();

        String qGetWindow =

                "PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +

                        " SELECT DISTINCT ?w \n" +
                        " WHERE { \n" +
                        ruleIRI + " a sm4cep:Rule . \n" +
                        ruleIRI + " sm4cep:hasWindow ?w . \n" +
                        "?w a sm4cep:Window . \n" +
                        "} ";

        ResultSet results = this.runAQuery(qGetWindow, endpoint);

        if (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            if (results.hasNext())
                throw new WindowException("Rule has more than one window!");

            RDFNode windowNode = soln.get("w");
            String windowIRI = formatIRI(windowNode.toString());

            window.setIRI(windowIRI);

            // set window kind/type
            WindowType windowType = this.getWindowKind(windowIRI);
            window.setWindowType(windowType);

            // set window unit
            WindowUnit windowUnit = this.getWindowUnit(windowIRI);
            window.setWindowUnit(windowUnit);

        } else {
            throw new WindowException("Rule has no window!");
        }

        return window;
    }

    // get window kind/type
    public WindowType getWindowKind(String windowIRI) throws WindowException {

        WindowType windowType = null;

        String qGetWindowKind =

                "PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +

                        " SELECT DISTINCT ?windowKind \n" +
                        " WHERE { \n" +
                        windowIRI + " sm4cep:hasWindowAttribute ?windowKind . \n" +
                        "?windowKind a sm4cep:WindowKind . \n" +
                        "} ";

        ResultSet results = this.runAQuery(qGetWindowKind, endpoint);


        if (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            if (results.hasNext())
                throw new WindowException("Rule has more than one window kind!");

            RDFNode windowKindNode = soln.get("windowKind");
            String windowKind = formatIRI(windowKindNode.toString());

            if (windowKind.equalsIgnoreCase("sm4cep:SlidingWindow") ||
                    windowKind.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/SlidingWindow") ||
                    windowKind.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/SlidingWindow>")) {
                windowType = WindowType.SLIDING_WINDOW;
            } else if (windowKind.equalsIgnoreCase("sm4cep:TumblingWindow") ||
                    windowKind.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/TumblingWindow") ||
                    windowKind.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/TumblingWindow>")) {
                windowType = WindowType.TUMBLING_WINDOW;
            }

        } else {
            throw new WindowException("Rule has no window kind!");
        }

        return windowType;
    }

    // get window unit
    public WindowUnit getWindowUnit(String windowIRI) throws WindowException {

        WindowUnit windowUnit = null;

        String qGetWindowUnit =

                "PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +

                        " SELECT DISTINCT ?windowUnit \n" +
                        " WHERE { \n" +
                        windowIRI + " sm4cep:hasWindowAttribute ?windowUnit . \n" +
                        "?windowUnit a sm4cep:WindowUnit . \n" +
                        "} ";

        ResultSet results = this.runAQuery(qGetWindowUnit, endpoint);


        if (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            if (results.hasNext())
                throw new WindowException("Rule has more than one window unit!");

            RDFNode windowUnitNode = soln.get("windowUnit");
            String windowUnitString = formatIRI(windowUnitNode.toString());

            if (windowUnitString.equalsIgnoreCase("sm4cep:TimeUnit") ||
                    windowUnitString.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/TimeUnit") ||
                    windowUnitString.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/TimeUnit>")) {
                windowUnit = WindowUnit.TIME;
            } else if (windowUnitString.equalsIgnoreCase("sm4cep:EventUnit") ||
                    windowUnitString.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/EventUnit") ||
                    windowUnitString.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/EventUnit>")) {
                windowUnit = WindowUnit.EVENT; 
            }

        } else {
            throw new WindowException("Rule has no window unit!");
        }

        return windowUnit;
    }

    
    // get CEP elements for a given rule
    public CEPElement getCEPElement(String ruleIRI) {
        Event event = null;

        return event;
    }

    
    // get event
    /*public Event getEvent(String eventIRI) {
    	Event event = new Event(eventIRI);
    	
    	EventSchema eventSchema = this.getEventSchema();
    	
    	
        SimpleEvent simpleEvent = new SimpleEvent();
        simpleEvent.setAttributes(new LinkedList<Attribute>());

        simpleEvent.setEventName(eventIRI);// TODO: check if there needs to be the event IRI and the event name

        String qGetSimpleEvent =

                "PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +

                        " SELECT DISTINCT ?eventAttribute \n" +
                        " WHERE { \n" +
                        eventIRI + " sm4cep:hasEventAttribute ?eventAttribute . \n" +
                        "?eventAttribute a sm4cep:EventAttribute . \n" +
                        "} ";

        ResultSet results = this.runAQuery(qGetSimpleEvent, endpoint);


        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            RDFNode eventAttributeNode = soln.get("eventAttribute");
            String eventAttributeString = formatIRI(eventAttributeNode.toString());

            Attribute eventAttribute = new Attribute(eventAttributeString, AttributeType.TYPE_STRING); // TODO: here there can be smarter deciding but then we need instances or explicit info about this
            simpleEvent.addAttribute(eventAttribute);
        }

        return simpleEvent;
    }*/

    // get event for an event IRI
    public Event getEvent (String eventIRI) throws CEPElementException {
    	Event event = new Event (eventIRI);
    	
    	EventSchema eventSchema = this.getEventSchema(eventIRI);
    	event.setEventSchema(eventSchema);
    	
    	// TODO: get filters
    	
    	return event;
    }
    
    // get event schema for an event IRI
    public EventSchema getEventSchema (String eventIRI) throws CEPElementException{

    	EventSchema eventSchema = null;
    	
    	String qGetEventSchemaIRI =

                "PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +

                        " SELECT DISTINCT ?eventSchemaIRI \n" +
                        " WHERE { \n" +
                        eventIRI + " sm4cep:hasEventSchema ?eventSchemaIRI . \n" +
                        "?eventSchemaIRI a sm4cep:EventSchema . \n" +
                        "} ";

        ResultSet results = this.runAQuery(qGetEventSchemaIRI, endpoint);
        String eventSchemaIRI = "";
    	
        if (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            RDFNode eventSchemaNode = soln.get("eventSchemaIRI");
            eventSchemaIRI = formatIRI(eventSchemaNode.toString());
        }
	    else {
	    	throw new CEPElementException("Event does not have schema defined!");
	    }
    	if (results.hasNext()){
    		throw new CEPElementException("Event has more than 1 schema defined!");
    	}
        
    	if (this.eventSchemata.containsKey(eventSchemaIRI)){ // event schema already exists
    		eventSchema = this.eventSchemata.get(eventSchemaIRI);
    	}
    	else { // we need to retrieve the element schema
    		eventSchema = new EventSchema(eventSchemaIRI);
    		
    		String qGetEventSchemaAttributes =

                    "PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +

                            " SELECT DISTINCT ?eventAttribute \n" +
                            " WHERE { \n" +
                            eventSchemaIRI + " sm4cep:hasEventAttribute ?eventAttribute . \n" +
                            "?eventAttribute a sm4cep:EventAttribute . \n" +
                            "} ";

            ResultSet results2 = this.runAQuery(qGetEventSchemaAttributes, endpoint);

            while (results2.hasNext()) {
                QuerySolution soln2 = results2.nextSolution();

                RDFNode eventAttributeNode = soln2.get("eventAttribute");
                String eventAttributeString = formatIRI(eventAttributeNode.toString());

                Attribute eventAttribute = new Attribute(eventAttributeString); // TODO: we don't know element type here... in principle, we can always set it to string as default value
                eventAttribute.setEvent(eventSchema);
                this.eventAttributes.put(eventAttributeString, eventAttribute); // add attribute to the list of all attributes (needed for the filters definition)
                eventSchema.addAttribute(eventAttribute); // TODO: here we can add duplicate check, i.e., if the list already contains this elements                
            }
    	}
    	
    	if (eventSchema != null)
    		return eventSchema;
    	else
    		throw new CEPElementException ("No event schema...");
    }
    
    // get filters in a rule for an event
    public List<SimpleClause> getFiltersOverEvent (String ruleIRI, String eventSchemaIRI){
    	
    	ArrayList<String> filterIRIs = new ArrayList<String> (); // list of all filters, i.e., simple clauses in a rule for an event
    	
    	String qGetFilters =

                "PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +

                        " SELECT DISTINCT ?filter \n" +
                        " WHERE { \n" +
                        ruleIRI + " sm4cep:hasFilter ?filter . \n" +
                        "?filter a sm4cep:SimpleClause . \n" +
                        "?filter sm4cep:hasLeftOperand ?attribute . \n" +
                        eventSchemaIRI + " sm4cep:hasEventAttribute ?attribute . \n" + // TODO: this might be implemented in the other way such that we iterate over the attribute hashmap
                        "} ";

        ResultSet results = this.runAQuery(qGetFilters, endpoint);

        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            RDFNode filterNode = soln.get("filter");
            String filterString = formatIRI(filterNode.toString());
            filterIRIs.add(filterString);           
        }
    	
    	return null; 
    }
    
    public SimpleClause getFilter (String filterIRI) throws CEPElementException{ // here the assumption of well formatting is that the attribute operation is always on the left side
    	SimpleClause filter = new SimpleClause(filterIRI);
    	
    	String qGetFilter =

                "PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +

                        " SELECT DISTINCT ?left ?leftType ?right ?rightType ?operator \n" +
                        " WHERE { \n" +
                        filterIRI + " sm4cep:hasLeftOperand ?left . \n" +
                        "?left a ?leftType . \n" +
                        filterIRI + " sm4cep:hasRightOperand ?right . \n" +
                        "?right a ?rightType . \n" +
                        filterIRI + " sm4cep:hasComparisonOperator ?operator . \n" +
                        "} ";

        ResultSet results = this.runAQuery(qGetFilter, endpoint);

        if (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            RDFNode leftTypeNode = soln.get("leftType");
            String leftTypeIRI = formatIRI(leftTypeNode.toString());
            // TODO: Continue here...
        }
        else {
        	throw new CEPElementException ("Filter misses  internals!");
        }
    	
    	return null; 
    }
    
    
    /*Iterator it = this.hierarchies.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        String hierarchyIRI = pair.getKey().toString();
        int value = (int)pair.getValue();	    
        
        s += "\t" + hierarchyIRI + ": " + value + "\n";  
    }*/
    
    // get complex event
/*	public ComplexTemporalEvent getComplexTemporalEvent(String eventIRI) {
		ComplexTemporalEvent complexTemporalEvent = new ComplexTemporalEvent() ;
		//complexTemporalEvent.setEvents(new LinkedList<Event>()); // TODO: remove this if a new constructor is added that automatically does this

        String qGetTemporalOperator =

        		"PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +
        		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +

        		" SELECT DISTINCT ?temporalOperator ?offset \n" +
        		" WHERE { \n" +
        		eventIRI + " sm4cep:usesTemporalOperator ?temporalOperator . \n" +
        		"?temporalOperator a ?midlewareClass . \n" +
        		"?midlewareClass rdfs:subClassOf sm4cep:TemporalOperator . \n" +
        		"OPTIONAL {?temporalOperator sm4cep:hasOffset ?offset } \n" +
        		"} ";

        ResultSet results = this.runAQuery(qGetTemporalOperator, endpoint);


        while ( results.hasNext() ) {
      	  	QuerySolution soln = results.nextSolution() ;

      	  	RDFNode temporalOperatorNode = soln.get("temporalOperator");
      	  	String temporalOperatorString = formatIRI(temporalOperatorNode.toString());

      	  	RDFNode offsetNode = soln.get("offset");
    	  	String offsetString = formatIRI(offsetNode.toString());

      	  	if (temporalOperatorString.equalsIgnoreCase("sm4cep:WithIn") ||
      	  		temporalOperatorString.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/WithIn") ||
      	  			temporalOperatorString.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/WithIn>")) {
    	  		complexTemporalEvent.setTemporalOperator(new TemporalOperator(TemporalOperatorEnum.Within),);
    	  	}
    	  	else if (temporalOperatorString.equalsIgnoreCase("sm4cep:Sequence") ||
    	  			temporalOperatorString.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/Sequence") ||
    	  				temporalOperatorString.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/Sequence>")) {
    	  		complexTemporalEvent.setTemporalOperator(new TemporalOperator(TemporalOperatorEnum.Sequence));
        }

		return simpleEvent;
	}
	*/
    // get the list of events for a complex event
    /*public LinkedList<Event> getComplexEventList(String eventIRI) {

        LinkedList<Event> eventList = new LinkedList<Event>();

        String qGetEventList =

                "PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" +

                        " SELECT DISTINCT ?event ?order \n" +
                        " WHERE { \n" +
                        eventIRI + " sm4cep:containsEvent ?includedEvent . \n" +
                        "?includedEvent sm4cep:representsEvent ?event . \n" +
                        "?includedEvent sm4cep:hasEventOrder ?order . \n" +
                        "} ";

        ResultSet results = this.runAQuery(qGetEventList, endpoint);


        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            RDFNode eventNode = soln.get("event");
            String eventString = formatIRI(eventNode.toString());

            RDFNode orderNode = soln.get("order");
            int order = -1;
            try {
                order = orderNode.asLiteral().getInt();
            } catch (Exception e) {

                System.out.println("Error with parsing event list element order: " + e);
            }

            Event eventInList = this.getEvent(eventString); // this will be recursive call until it is resolved with simple elements or a complex element that has no elements
            try {
                eventList.add(order, eventInList);
            } catch (IndexOutOfBoundsException ioobx) {
                eventList.add(eventInList); // if the element doesn't have element specified or is greater than the current number of elements in the list, it will be added to the end
            }
        }

        return eventList;
    }*/

    private ResultSet runAQuery(String sparqlQuery, String endpoint) {
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qExe = QueryExecutionFactory.sparqlService(endpoint, query);
        ResultSet results = qExe.execSelect();

        return results;
    }

    // format IRI if it is not surrounded with brackets and it should be
    public String formatIRI(String iri) {
        if (iri.startsWith("http://"))
            return "<" + iri + ">";
        else
            return iri;
    }

    // getters and setters
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    // stringing methods
    public String stringWindow(Window w) {
        String text = "";
        text += "Window with values: \n";
        text += "  window kind: " + w.getWindowType().toString() + "\n";
        if (w.getTimeUnit() != null)
            text += "  window unit: " + w.getTimeUnit().toString() + "\n";
        else
            text += " window unit: event unit";

        return text;
    }
/*****
    public String stringSimpleEvent(SimpleEvent se) {
        String text = "";
        text += "Simple event: " + se.getEventName() + "\n";
        List<Attribute> attributes = se.getAttributes();
        int size = attributes.size();
        for (int i = 0; i < size; i++) {
            text += "  has attribute: " + attributes.get(i).getName() + "\n";
        }

        return text;
    }
    *****/
}