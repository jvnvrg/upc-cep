package upc.edu.cep.sm4cep;

public class Sm4cepParserTests {

	public void testCompleteCondition(Sm4cepParser parser) {
		try { 
			parser.getCondition("<http://testIRI>");
		}
		catch (ConditionException ex) {
			System.out.println("Complete Condition Test Exception: " + ex);
		}
	}
}
