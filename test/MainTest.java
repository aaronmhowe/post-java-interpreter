import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Entry point for the test cases implemented in InterpreterTest
 * @author Aaron Howe
 * @version Java 11
 */
public class MainTest {
    /**
     * Main application method to execute test cases for the post-script-interpreter.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        Result test = JUnitCore.runClasses(InterpreterTest.class);

        for (Failure failure : test.getFailures()) {
            System.out.println(failure.toString());
        }

        if (test.wasSuccessful()) {
            System.out.println("All Tests Passed.");
            System.out.println("Test Count: " + test.getRunCount());
        }
    }
}