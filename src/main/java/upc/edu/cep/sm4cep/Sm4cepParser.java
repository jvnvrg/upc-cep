package upc.edu.cep.sm4cep;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;

import upc.edu.cep.RDF_Model.Operators.ComparasionOperator;
import upc.edu.cep.RDF_Model.Operators.ComparasionOperatorEnum;
import upc.edu.cep.RDF_Model.Operators.LogicOperator;
import upc.edu.cep.RDF_Model.Operators.LogicOperatorEnum;
import upc.edu.cep.RDF_Model.Operators.Sequence;
import upc.edu.cep.RDF_Model.Operators.TemporalOperator;
import upc.edu.cep.RDF_Model.Operators.TimeUnit;
import upc.edu.cep.RDF_Model.Operators.Within;
import upc.edu.cep.RDF_Model.Rule;
import upc.edu.cep.RDF_Model.condition.LiteralOperand;
import upc.edu.cep.RDF_Model.condition.Operand.OperandType;
import upc.edu.cep.RDF_Model.condition.SimpleClause;
import upc.edu.cep.RDF_Model.event.Attribute;
import upc.edu.cep.RDF_Model.event.AttributeType;
import upc.edu.cep.RDF_Model.event.CEPElement;
import upc.edu.cep.RDF_Model.event.Event;
import upc.edu.cep.RDF_Model.event.EventSchema;
import upc.edu.cep.RDF_Model.event.LogicPattern;
import upc.edu.cep.RDF_Model.event.Pattern;
import upc.edu.cep.RDF_Model.event.TemporalPattern;
import upc.edu.cep.RDF_Model.event.TimeEvent;
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

	private String sm4cepNamespace = "http://www.essi.upc.edu/~jvarga/sm4cep/"; // the namespace address
	
    private String endpoint = "http://localhost:8890/sparql";
    private HashMap<String, EventSchema> eventSchemata = new HashMap<String, EventSchema> (); // all event schemata, something like event schemata cash 
    private HashMap<String, Attribute> eventAttributes = new HashMap<String, Attribute> (); // all event attributes, something like event attributes cash

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

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

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

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

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

            if (this.equalsToSm4cepElement(windowKind, "SlidingWindow")) {
                windowType = WindowType.SLIDING_WINDOW;
            } else if (this.equalsToSm4cepElement(windowKind, "TumblingWindow")) {
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

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

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

            if (this.equalsToSm4cepElement(windowUnitString, "TimeUnit")) {
                windowUnit = WindowUnit.TIME;
            }  else if (this.equalsToSm4cepElement(windowUnitString, "EventUnit")) {
                windowUnit = WindowUnit.EVENT; 
            }

        } else {
            throw new WindowException("Rule has no window unit!");
        }

        return windowUnit;
    }

    /*****   GET CEP ELEMENT   *****/
    
    // get CEP elements for a given rule
    public CEPElement getCEPElement(String cepElementIRI, String ruleIRI) throws CEPElementException {
        CEPElement cepElement = null;
        
        String qGetElementType =

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

                        " SELECT DISTINCT ?elementType \n" +
                        " WHERE { \n" +
                        cepElementIRI + " a ?elementType . \n" +
                        "} ";
        
        ResultSet results = this.runAQuery(qGetElementType, endpoint);

        if (results.hasNext()) { // ASSUMPTION IS THAT EACH RULE HAS A SINGLE CEP ELEMENT TYPE
            QuerySolution soln = results.nextSolution();

            RDFNode elementTypeNode = soln.get("elementType");
            String elementType = formatIRI(elementTypeNode.toString());
            
            if (this.equalsToSm4cepElement(elementType, "Event"))
            	cepElement = this.getEventInRule(cepElementIRI, ruleIRI);
            else if (this.equalsToSm4cepElement(elementType, "TimeEvent"))
            	cepElement = this.getTimeEvent(cepElementIRI);
            else if (this.equalsToSm4cepElement(cepElementIRI, "Pattern"))
            	cepElement = this.getPattern(cepElementIRI, ruleIRI);
        }
        else {
        	throw new CEPElementException("The rule has no CEP elements!");
        }
        if (results.hasNext()) {
        	throw new CEPElementException("The rule has more than one CEP element!");            
        }

        return cepElement;
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

    /*****   GET EVENT   *****/
    
    // get event for an event IRI
    public Event getEventInRule (String eventIRI, String ruleIRI) throws CEPElementException {
    	Event event = new Event (eventIRI);
    	
    	EventSchema eventSchema = this.getEventSchema(eventIRI);
    	event.setEventSchema(eventSchema);
    	
    	LinkedList<SimpleClause> filters = this.getFiltersOverEvent(ruleIRI, eventIRI);
    	event.setFilters(filters);
    	
    	return event;
    }
    
    // get event schema for an event IRI
    public EventSchema getEventSchema (String eventIRI) throws CEPElementException{

    	EventSchema eventSchema = null;
    	
    	String qGetEventSchemaIRI =

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

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

                    "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

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
    
    // get filters for an event
    public LinkedList<SimpleClause> getFiltersOverEvent (String ruleIRI, String eventIRI) throws CEPElementException{
    	
    	LinkedList<SimpleClause> filters = new LinkedList<SimpleClause> (); // list of all filters, i.e., simple clauses in a rule for an event
    	
    	String qGetFilters =

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

                        " SELECT DISTINCT ?filter \n" +
                        " WHERE { \n" +
                        ruleIRI + " sm4cep:hasFilter ?filter . \n" + 
                        " ?filter a sm4cep:SimpleClause . \n" +
                        " ?filter sm4cep:hasLeftOperand ?attribute . \n" +
                        " ?attribute sm4cep:forEvent " + eventIRI + " . \n" +                         
                        "} ";

        ResultSet results = this.runAQuery(qGetFilters, endpoint);

        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            RDFNode filterNode = soln.get("filter");
            String filterString = formatIRI(filterNode.toString());
            filters.add(this.getFilter(filterString));           
        }
        
    	return filters; 
    }
    
    
    /* // INITIAL VERSION until the SM4CEP change from 12/6/2017 // get filters in a rule for an event
    public LinkedList<SimpleClause> getFiltersOverEvent (String ruleIRI, String eventSchemaIRI) throws CEPElementException{
    	
    	LinkedList<SimpleClause> filters = new LinkedList<SimpleClause> (); // list of all filters, i.e., simple clauses in a rule for an event
    	
    	String qGetFilters =

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

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
            filters.add(this.getFilter(filterString));           
        }
        
    	return filters; 
    }*/
    
    public SimpleClause getFilter (String filterIRI) throws CEPElementException{ // here the assumption of well formatting is that the attribute operand is always on the left side, 
    	// while the right operand is literal and the operator needs to be an SM4CEP comparison operator
    	SimpleClause filter = new SimpleClause(filterIRI);
    	
    	String qGetFilter =

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

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

            // getting left (i.e., number 1) operand
            RDFNode leftTypeNode = soln.get("leftType");
            String leftTypeIRI = formatIRI(leftTypeNode.toString());
            if (this.equalsToSm4cepElement(leftTypeIRI, "UsedAttribute")) {
                 
            	RDFNode leftNode = soln.get("left");
                String leftOperandIRI = formatIRI(leftNode.toString());
                
                String qGetAttributeType =

                        "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

                                " SELECT DISTINCT ?attributeType \n" +
                                " WHERE { \n" +
                                leftOperandIRI + " sm4cep:forAttribute ?attributeType . \n" +
                                "} ";
                
                ResultSet results2 = this.runAQuery(qGetAttributeType, endpoint);
                String attributeTypeIRI;
                if (results2.hasNext()) {
                    QuerySolution soln2 = results2.nextSolution();

                    // getting left (i.e., number 1) operand
                    RDFNode attributeTypeNode = soln2.get("attributeType");
                    attributeTypeIRI = formatIRI(attributeTypeNode.toString());
                }
                else {
                	throw new CEPElementException("Used attribute does not have event attribute type defined!");
                }
                if (results2.hasNext()) {
                	throw new CEPElementException("Used attribute has more than one event attribute type defined!");            
                }
                               
                Attribute eAttribute = this.eventAttributes.get(attributeTypeIRI); // the event attribute should have been already retrieved with the event schema
                if (eAttribute != null){
                	eAttribute.setOperandType(OperandType.other); // since it is not 'group by' neither 'having', it is 'other'
                	filter.setOperand1(eAttribute);
                }
                else {
                	throw new CEPElementException ("The definition of the event attribute is not retrieved with the event schema definition!");
                }                
            }
            else {
            	throw new CEPElementException("Left operand is not (defined as) an event attribute!");
            }
            
            // getting right (i.e., number 2) operand
            RDFNode rightTypeNode = soln.get("rightType");
            String rightTypeIRI = formatIRI(rightTypeNode.toString());
            if (this.equalsToSm4cepElement(rightTypeIRI, "Literal")) {
                 
            	RDFNode rightNode = soln.get("right");
                String rightOperand = formatIRI(rightNode.toString());             
                if (rightOperand != null){
                	LiteralOperand rightOp = new LiteralOperand();
                	rightOp.setValue(rightOperand);
                	rightOp.setOperandType(OperandType.other); // since it is a literal it belongs to 'other'
                	filter.setOperand2(rightOp);
                }
                else {
                	throw new CEPElementException ("The literal in the filter is not defined!");
                }                
            }
            else {
            	throw new CEPElementException("Right operand is not (defined as) an event attribute!");
            }     
            
         	// getting operator
            RDFNode operatorNode = soln.get("operator");
            String operatorIRI = formatIRI(operatorNode.toString());
            /*ComparasionOperator operator = null;
            
            if (this.equalsToSm4cepElement(operatorIRI, "Equal"))
            	operator = new ComparasionOperator(ComparasionOperatorEnum.EQ);
            else if (this.equalsToSm4cepElement(operatorIRI, "NotEqual"))
            	operator = new ComparasionOperator(ComparasionOperatorEnum.NE);
            else if (this.equalsToSm4cepElement(operatorIRI, "GreaterThan"))
            	operator = new ComparasionOperator(ComparasionOperatorEnum.GT);
            else if (this.equalsToSm4cepElement(operatorIRI, "GreaterOrEqual"))
            	operator = new ComparasionOperator(ComparasionOperatorEnum.GE);
            else if (this.equalsToSm4cepElement(operatorIRI, "LessThan"))
            	operator = new ComparasionOperator(ComparasionOperatorEnum.LT);
            else if (this.equalsToSm4cepElement(operatorIRI, "LessOrEqual"))
            	operator = new ComparasionOperator(ComparasionOperatorEnum.LE);
            */
            ComparasionOperator operator = this.getComparisonOperator(operatorIRI);
            
            if (operator != null) {
            	filter.setOperator(operator);
            }
            else {
            	throw new CEPElementException("Operator does not belong to the SM4CEP comparison operators!");
            }
        }
        else {
        	throw new CEPElementException ("Filter misses  internals!");
        }
        if (results.hasNext()) {
        	throw new CEPElementException("More than one result for elements belonging to a single filter!"); 
        }
    	
    	return filter; 
    }
    
    
    /*****   GET TIME EVENT   *****/
    
    // get time event event IRI
    public TimeEvent getTimeEvent (String timeEventIRI) throws CEPElementException {
    	TimeEvent timeEvent = new TimeEvent (timeEventIRI);
    	
    	String qGetTimeStamp =

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

                        " SELECT DISTINCT ?timeStamp \n" +
                        " WHERE { \n" +
                        timeEventIRI + " sm4cep:hasTimeStamp ?timeStamp . \n" +
                        "} ";
        
        ResultSet results2 = this.runAQuery(qGetTimeStamp, endpoint);

        if (results2.hasNext()) {
            QuerySolution soln2 = results2.nextSolution();

            RDFNode timeStampNode = soln2.get("timeStamp");
            String timeStamp = formatIRI(timeStampNode.toString());
            
            timeEvent.setTimestamp(timeStamp);
        }
        else {
        	throw new CEPElementException("Time event does not have time stamp!");
        }
        if (results2.hasNext()) {
        	throw new CEPElementException("Time event has more than one time stamp!");            
        }
    	
    	return timeEvent;
    }
    
    
    /*Iterator it = this.hierarchies.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        String hierarchyIRI = pair.getKey().toString();
        int value = (int)pair.getValue();	    
        
        s += "\t" + hierarchyIRI + ": " + value + "\n";  
    }*/
    
    /*****   GET PATTERN   *****/
    
    // get CEP pattern
    public Pattern getPattern(String patternIRI, String ruleIRI) throws CEPElementException {
    	Pattern pattern = null;
        
        String qGetPatternType =

                "PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +

                        " SELECT DISTINCT ?patternType \n" +
                        " WHERE { \n" +
                        patternIRI + " a ?patternType . \n" +
                        "} ";
        
        ResultSet results = this.runAQuery(qGetPatternType, endpoint);

        if (results.hasNext()) { // ASSUMPTION IS THAT A PATTERN IS OF A SINGLE TYPE
            QuerySolution soln = results.nextSolution();

            RDFNode patternTypeNode = soln.get("patternType");
            String patternType = formatIRI(patternTypeNode.toString());
            
            if (this.equalsToSm4cepElement(patternType, "TemporalPattern"))
            	pattern = this.getTemporalPattern(patternIRI, ruleIRI);
            else if (this.equalsToSm4cepElement(patternType, "LogicPattern"))
            	pattern = this.getLogicPattern(patternIRI, ruleIRI);
        }
        else {
        	throw new CEPElementException("The pattern has no type!");
        }
        if (results.hasNext()) {
        	throw new CEPElementException("The pattern has more than one type!");            
        }
    	
    	return pattern;
    }
    
    // get complex event
	public TemporalPattern getTemporalPattern(String patternIRI, String ruleIRI) throws CEPElementException {
		TemporalPattern temporalPattern = new TemporalPattern(patternIRI) ;

        String qGetTemporalOperator =

        		"PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +
        		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +

        		" SELECT DISTINCT ?temporalOperator \n" +
        		" WHERE { \n" +
        		patternIRI + " sm4cep:usesTemporalOperator ?temporalOperator . \n" +
        		"} ";

        ResultSet results = this.runAQuery(qGetTemporalOperator, endpoint);

        if (results.hasNext()) { // ASSUMPTION IS THAT A PATTERN IS OF A SINGLE TYPE
            QuerySolution soln = results.nextSolution();

            RDFNode operatorNode = soln.get("temporalOperator");
            String operator = formatIRI(operatorNode.toString());
            
            temporalPattern.setTemporalOperator(this.getTemporalOperator(operator));
        }
        else {
        	throw new CEPElementException("The temporal pattern has no operator!");
        }
        if (results.hasNext()) {
        	throw new CEPElementException("The temporal pattern has more than one operator!");            
        }
        
        temporalPattern.setCEPElements(this.getCEPElementsList(patternIRI, ruleIRI));
        

		return temporalPattern;
	}
	
	// get complex event
	public LogicPattern getLogicPattern(String patternIRI, String ruleIRI) throws CEPElementException {
		LogicPattern logicPattern = new LogicPattern(patternIRI) ;

        String qGetLogicOperator =

        		"PREFIX sm4cep: <" + sm4cepNamespace + "> \n" +
        		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +

        		" SELECT DISTINCT ?logicOperator \n" +
        		" WHERE { \n" +
        		patternIRI + " sm4cep:usesLogicOperator ?logicOperator . \n" +
        		"} ";

        ResultSet results = this.runAQuery(qGetLogicOperator, endpoint);

        if (results.hasNext()) { // ASSUMPTION IS THAT A PATTERN IS OF A SINGLE TYPE
            QuerySolution soln = results.nextSolution();

            RDFNode operatorNode = soln.get("logicOperator");
            String operator = formatIRI(operatorNode.toString());
            
            logicPattern.setLogicOperator(this.getLogicOperator(operator));
        }
        else {
        	throw new CEPElementException("The logic pattern has no operator!");
        }
        if (results.hasNext()) {
        	throw new CEPElementException("The logic pattern has more than one operator!");            
        }
        
        logicPattern.setCEPElements(this.getCEPElementsList(patternIRI, ruleIRI));
        

		return logicPattern;
	}
	
    // get the list of events for an event pattern
    public LinkedList<CEPElement> getCEPElementsList(String patternIRI, String ruleIRI) throws CEPElementException {

        LinkedList<CEPElement> elementsList = new LinkedList<CEPElement>();

        String qGetPatternList =

                "PREFIX sm4cep: <" + this.sm4cepNamespace + "> \n" +

                        " SELECT DISTINCT ?element ?order \n" +
                        " WHERE { \n" +
                        patternIRI + " sm4cep:containsElement ?includedElement . \n" +
                        "?includedElement sm4cep:representsElement ?element . \n" +
                        "?includedElement sm4cep:hasElementOrder ?order . \n" +
                        "} ";

        ResultSet results = this.runAQuery(qGetPatternList, endpoint);


        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();

            RDFNode elementNode = soln.get("element");
            String elementString = formatIRI(elementNode.toString());

            RDFNode orderNode = soln.get("order");
            int order = -1;
            try {
                order = orderNode.asLiteral().getInt();
            } catch (Exception e) {

                System.out.println("Error with parsing event list element order: " + e);
            }

            CEPElement elementInList = this.getCEPElement(elementString, ruleIRI); // this will be recursive call until it is resolved with simple elements or a complex element that has no elements
            try {
                elementsList.add(order, elementInList);
            } catch (IndexOutOfBoundsException ioobx) {
            	elementsList.add(elementInList); // if the element doesn't have element specified or is greater than the current number of elements in the list, it will be added to the end
            }
        }

        return elementsList;
    }
    
    /*****   GET OPERATORS   *****/
    
    // get temporal operators - here we focus on within and sequence operators that are defined in our ontology
    public TemporalOperator getTemporalOperator(String operatorIRI) throws CEPElementException  {
    	TemporalOperator tempOp = null;
    	
    	if (this.equalsToSm4cepElement(operatorIRI, "Sequence")) {
    		tempOp = new Sequence(operatorIRI);
    	} else if (this.equalsToSm4cepElement(operatorIRI, "WithIn")) {
    		String qGetWithInOffset =

            		"PREFIX sm4cep: <" + this.sm4cepNamespace + "> \n" + 
            		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +

            		" SELECT DISTINCT ?temporalOperator ?offset \n" +
            		" WHERE { \n" +
            		operatorIRI + " a sm4cep:OperatorWithExplicitTime . \n" + 
            		operatorIRI + " sm4cep:hasOffset ?offset . \n" +
            		"} ";

            ResultSet results = this.runAQuery(qGetWithInOffset, endpoint);
            
            if (results.hasNext()) {
                QuerySolution soln = results.nextSolution();

                // getting left (i.e., number 1) operand
                RDFNode offsetNode = soln.get("offset");
                int offset = offsetNode.asLiteral().getInt(); 
                //String offset = formatIRI(offsetNode.toString());
                  
    	        tempOp = new Within(offset, TimeUnit.millisecond, operatorIRI); // NOTE: Here we assume that the offset is in milliseconds
            }
            else {
            	throw new CEPElementException("There is no offset for the within operator!");
            }
            if (results.hasNext()) {
            	throw new CEPElementException("There is more than one offset for the within operator!");            
            }
    	}
    	
        return tempOp;
    }
    
    // get logic operator - covering the ones available in the ontology
    public LogicOperator getLogicOperator(String operatorIRI) throws CEPElementException {
    	LogicOperator operator = null; 
    	
    	if (this.equalsToSm4cepElement(operatorIRI, "Conjunction"))
        	operator = new LogicOperator(LogicOperatorEnum.Conjunction);
        else if (this.equalsToSm4cepElement(operatorIRI, "Disjunction"))
        	operator = new LogicOperator(LogicOperatorEnum.Disjunction);
        else if (this.equalsToSm4cepElement(operatorIRI, "Negation"))
        	operator = new LogicOperator(LogicOperatorEnum.Negation);
    	
    	return operator;
    }
    
    // get comparison operator - focusing on the operators specified in our ontology
    public ComparasionOperator getComparisonOperator(String operatorIRI) {
        ComparasionOperator operator = null;
        
        if (this.equalsToSm4cepElement(operatorIRI, "Equal"))
        	operator = new ComparasionOperator(ComparasionOperatorEnum.EQ);
        else if (this.equalsToSm4cepElement(operatorIRI, "NotEqual"))
        	operator = new ComparasionOperator(ComparasionOperatorEnum.NE);
        else if (this.equalsToSm4cepElement(operatorIRI, "GreaterThan"))
        	operator = new ComparasionOperator(ComparasionOperatorEnum.GT);
        else if (this.equalsToSm4cepElement(operatorIRI, "GreaterOrEqual"))
        	operator = new ComparasionOperator(ComparasionOperatorEnum.GE);
        else if (this.equalsToSm4cepElement(operatorIRI, "LessThan"))
        	operator = new ComparasionOperator(ComparasionOperatorEnum.LT);
        else if (this.equalsToSm4cepElement(operatorIRI, "LessOrEqual"))
        	operator = new ComparasionOperator(ComparasionOperatorEnum.LE);
        
        return operator;
    }
    
    /*****   RDF MANAGEMENT AND QUERYING SPARQL ENDPOINT   *****/

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
    
    // compare if a string belongs to an sm4cep element
    public boolean equalsToSm4cepElement(String retrievedIRI, String sm4cepElement ) {
    	if (retrievedIRI.equalsIgnoreCase("sm4cep:" + sm4cepElement) ||
    			retrievedIRI.equalsIgnoreCase(sm4cepNamespace + sm4cepElement) ||
    			retrievedIRI.equalsIgnoreCase("<"+ sm4cepNamespace + sm4cepElement +">"))
    		return true;
    	return false;
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