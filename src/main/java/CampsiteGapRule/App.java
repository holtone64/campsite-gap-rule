package CampsiteGapRule;

/**
 * @author holtone64
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CampsiteAvailability campsiteAvailability = 
				new CampsiteAvailability(CampsiteAvailability.readInputFile("resources/test-case.json"), 
						new AvailabilityDisplay());
	}

}
