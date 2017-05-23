package upc.edu.cep.sm4cep;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import upc.edu.cep.RDF_Model.*;
import upc.edu.cep.RDF_Model.action.*;
import upc.edu.cep.RDF_Model.condition.*;
import upc.edu.cep.RDF_Model.event.*;
import upc.edu.cep.RDF_Model.Operators.*;
import upc.edu.cep.RDF_Model.window.*;

import org.apache.jena.*;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.mgt.Explain;
import org.apache.jena.sparql.mgt.Explain.InfoLevel;
import org.apache.jena.system.JenaSystem;
import org.apache.jena.query.ARQ;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.log4j.Level;

public class Sm4cepParser {
	
	private String endpoint = "http://localhost:8890/sparql";

	public Sm4cepParser() {
	
	}
	
	public Sm4cepParser(String endpoint) {
		this.endpoint = endpoint;
	}

	public Rule getRule (String ruleIRI) {
		Rule rule = new Rule();
		
		try{
			Window window = this.getWindow(ruleIRI);
			rule.setWindow(window);
		} 
		catch (WindowException we){
			System.out.println("The rule has the following window exception: \n" + we);
		}
		
		
		return rule;
	}
	
	// get Window part from the SM4CEP triples (SM4CEP -> local java classes)
	
	public Window getWindow (String ruleIRI) throws WindowException {
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
        
        if ( results.hasNext() ) {        
      	  	QuerySolution soln = results.nextSolution() ;
      	  	
      	  	if (results.hasNext())
      	  		throw new WindowException("Rule has more than one window!");
      	  
      	  	RDFNode windowNode = soln.get("w");
      	  	String windowIRI = formatIRI(windowNode.toString());
      	  	
      	  	// TODO: add the setting of the rule IRI, e.g., window.setIRI(windowIRI);
      	  	
      	  	// set window kind/type
      	  	Window.WindowType windowType = this.getWindowKind(windowIRI);
      	  	window.setWindowType(windowType);
      	  	
      	  	// set window unit
      	  	TimeUnit timeUnit = this.getWindowUnit(windowIRI);
      	  	window.setTimeUnit(timeUnit);
      	  	
        }
        else {
        	throw new WindowException("Rule has no window!");
        }
		
		return window;
	}
	
	// get window kind/type
	public Window.WindowType getWindowKind (String windowIRI) throws WindowException {
		
		Window.WindowType windowType = null; 
		
        String qGetWindowKind = 

        		"PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" + 
        		
        		" SELECT DISTINCT ?windowKind \n" +
        		" WHERE { \n" +  
        		windowIRI + " sm4cep:hasWindowAttribute ?windowKind . \n" +
        		"?windowKind a sm4cep:WindowKind . \n" +
        		"} ";
        	
        ResultSet results = this.runAQuery(qGetWindowKind, endpoint); 
        
        
        if ( results.hasNext() ) {        
      	  	QuerySolution soln = results.nextSolution() ;
      	  	
      	  	if (results.hasNext())
      	  		throw new WindowException("Rule has more than one window kind!");
      	  
      	  	RDFNode windowKindNode = soln.get("windowKind");
      	  	String windowKind = formatIRI(windowKindNode.toString());
      	 
      	  	if (windowKind.equalsIgnoreCase("sm4cep:SlidingWindow") || 
      	  			windowKind.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/SlidingWindow") ||
      	  				windowKind.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/SlidingWindow>")) {
      	  		windowType = Window.WindowType.SLIDING_WINDOW;
      	  	}
      	  	else if (windowKind.equalsIgnoreCase("sm4cep:TumblingWindow") || 
      	  			windowKind.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/TumblingWindow") ||
  	  				windowKind.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/TumblingWindow>")) {
      	  		windowType = Window.WindowType.TUMBLING_WINDOW;
      	  	}
      	  	
        }
        else {
        	throw new WindowException("Rule has no window kind!");
        }
		
		return windowType;
	}
	
	public TimeUnit getWindowUnit (String windowIRI) throws WindowException {
		
		TimeUnit windowUnit = null; 
		
        String qGetWindowUnit = 

        		"PREFIX sm4cep: <http://www.essi.upc.edu/~jvarga/sm4cep/> \n" + 
        		
        		" SELECT DISTINCT ?windowUnit \n" +
        		" WHERE { \n" +  
        		windowIRI + " sm4cep:hasWindowAttribute ?windowUnit . \n" +
        		"?windowUnit a sm4cep:WindowUnit . \n" +
        		"} ";
        	
        ResultSet results = this.runAQuery(qGetWindowUnit, endpoint); 
        
        
        if ( results.hasNext() ) {        
      	  	QuerySolution soln = results.nextSolution() ;
      	  	
      	  	if (results.hasNext())
      	  		throw new WindowException("Rule has more than one window unit!");
      	  
      	  	RDFNode windowUnitNode = soln.get("windowUnit");
      	  	String windowUnitString = formatIRI(windowUnitNode.toString());
      	 
      	  	if (windowUnitString.equalsIgnoreCase("sm4cep:TimeUnit") || 
      	  			windowUnitString.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/TimeUnit") ||
      	  				windowUnitString.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/TimeUnit>")) {
      	  		windowUnit = TimeUnit.millisecond;
      	  	}
      	  	else if (windowUnitString.equalsIgnoreCase("sm4cep:EventUnit") || 
      	  			windowUnitString.equalsIgnoreCase("http://www.essi.upc.edu/~jvarga/sm4cep/EventUnit") ||
  	  				windowUnitString.equalsIgnoreCase("<http://www.essi.upc.edu/~jvarga/sm4cep/EventUnit>")) {
      	  		windowUnit = null; // TODO: this needs to be updated once this is addressed in local RDF java classes
      	  	}
      	  	
        }
        else {
        	throw new WindowException("Rule has no window unit!");
        }
		
		return windowUnit;
	}
	
	// get Event part of a Rule
	
	public Event getEvent(String ruleIRI) {
		Event event = null;
				
		return event;
	}
	
	// get simple event 
	public SimpleEvent getSimpleEvent(String eventIRI) {
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
        
        
        while ( results.hasNext() ) {        
      	  	QuerySolution soln = results.nextSolution() ;
      	  
      	  	RDFNode eventAttributeNode = soln.get("eventAttribute");
      	  	String eventAttributeString = formatIRI(eventAttributeNode.toString());
      	  	
      	  	Attribute eventAttribute = new Attribute(eventAttributeString, AttributeType.TYPE_STRING); // TODO: here there can be smarter deciding but then we need instances or explicit info about this
      	  	simpleEvent.addAttribute(eventAttribute);
        }

		return simpleEvent;
	}
	
	// get complex event
	public ComplexTemporalEvent getComplexTemporalEvent(String eventIRI) {
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
	
	// get the list of events for a complex event
	public LinkedList<Event> getComplexEventList(String eventIRI) {
		
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
        
        
        while ( results.hasNext() ) {        
      	  	QuerySolution soln = results.nextSolution() ;
      	  
      	  	RDFNode eventNode = soln.get("event");
      	  	String eventString = formatIRI(eventNode.toString());
      	  	
      	  	RDFNode orderNode = soln.get("order");
      	  	int order = -1;
      	  	try{
      	  		order = orderNode.asLiteral().getInt();
      	  	}
      	  	catch (Exception e) {
      	  		
      	  		System.out.println("Error with parsing event list element order: " + e);
      	  	}
      	  	
      	  	Event eventInList = this.getEvent(eventString); // this will be recursive call until it is resolved with simple elements or a complex element that has no elements
      	  	try {
      	  		eventList.add(order, eventInList);
      	  	}
      	  	catch (IndexOutOfBoundsException ioobx){
      	  		eventList.add(eventInList); // if the element doesn't have element specified or is greater than the current number of elements in the list, it will be added to the end
      	  	}
        }

		return eventList;
	}
	
	private ResultSet runAQuery(String sparqlQuery, String endpoint) {		
		Query query = QueryFactory.create(sparqlQuery); 
		QueryExecution qExe = QueryExecutionFactory.sparqlService( endpoint, query );
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
	public String stringWindow(Window w){
		String text = "";
		text += "Window with values: \n";
		text += "  window kind: " + w.getWindowType().toString() + "\n";
		if (w.getTimeUnit() != null)
			text += "  window unit: " + w.getTimeUnit().toString() + "\n";
		else
			text += " window unit: event unit";
		
		return text;
	}
	
	public String stringSimpleEvent(SimpleEvent se) {
		String text = "";
		text += "Simple event: " + se.getEventName() + "\n";
		List<Attribute> attributes = se.getAttributes();
		int size = attributes.size();
		for(int i = 0; i < size; i++) {
			text += "  has attribute: " + attributes.get(i).getName() + "\n";
		}
		
		return text;
	}
	
	public static void main(String[] args){
		
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
				System.out.println(p1.stringSimpleEvent(p1.getSimpleEvent(eventIRI)));
		
			} catch (Exception e){
				System.out.println("Error:\n" + e);
			}
		}
		System.out.println("Adeu!");

	}
}