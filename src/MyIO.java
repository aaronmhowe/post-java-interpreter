import java.util.*;

/**
 * Implements input/output operations.
 * @author Aaron Howe
 * @version Java 11
 */
public class MyIO {

    private final MyStack stack;

    /**
     * Constructor
     */
    public MyIO(MyStack stack) {
        this.stack = stack;
    }

    /**
     * Writes a string to standard output.
     * @throws EmptyStackException when the stack is empty.
     */
    public void print() {
        if (stack.count() < 1) {
            throw new EmptyStackException();
        }
        System.out.println(stack.pop());
    }

    /**
     * Pops an element from the operand stack and writes it as a string
     * to the standard output.
     * @throws EmptyStackException when the stack is empty.
     */
    public void printElement() {
        if (stack.count() < 1) {
            throw new EmptyStackException();
        }
        Object contents = stack.pop();
        System.out.println(elementToString(contents));
    }

    /**
     * Equal in nature to how printElement writes stack elements to the
     * standard output, but printArray writes the entire contents of an array.
     */
    public void printArray() {
        if (stack.count() < 1) {
            throw new EmptyStackException();
        }
        Object contents = stack.pop();
        System.out.println(subElementToString(contents));
    }

    /**
     * Converts an operand to string to be written to the standard output.
     * @return Popped operand converted to string formatted dependendent on the type of data returned by the operand.
     */
    private String elementToString(Object data) {
        // element is a string
        if (data instanceof String) {
            return "(" + data + ")";
        // element is a dictionary
        } else if (data instanceof Map) {
            return "-dict-";
        // element is a procedure
        } else if (data instanceof Runnable) {
            return "-procedure-";
        } else {
            return data.toString();
        }
    }

    /**
     * Converts an operand to string to be written to the standard output, with detailed array contents
     * displayed.
     * @return Popped operand converted to string formatted dependent on the type of data returned by the operand with
     * sub-data for each type.
     */
    private String subElementToString(Object data) {
        if (data instanceof String) {
            return "(" + data + ") -string-";
        } else if (data instanceof Map) {
            Map<?, ?> dictionary = (Map<?, ?>) data;
            return "-dict- contains " + dictionary.size() + " elements";
        } else if (data instanceof Runnable) {
            return "-procedure-";
        } else {
            return data.toString();
        }
    }
}