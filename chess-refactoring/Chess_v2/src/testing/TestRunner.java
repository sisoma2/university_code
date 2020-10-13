package testing;

import org.junit.runner.*;
import org.junit.runner.notification.*;

/**
 * Class TestRunner.
 * Runs all the tests
 */
public class TestRunner {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestSuite.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println("TEST METHODS OK? " + result.wasSuccessful());
	}
}
