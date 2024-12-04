import java.util.*;

/**
 * Implements boolean logic for values stored in an operand stack.
 * @author Aaron Howe
 * @version Java 11
 */
public class MyBoolean {

    private final MyStack stack;

    /**
     * Constructor.
     */
    public MyBoolean(MyStack stack) {
        this.stack = stack;
    }

    /**
     * Helper function for comparing operands.
     * @param op1 first operand.
     * @param op2 second operand.
     * @return Two operands in comparison.
     * @throws InputMismatchException when the operators in comparison are not of equal type and are not string or numeric.
     */
    private int comparisonHelper(Object op1, Object op2) {
        if (op1 instanceof Number && op2 instanceof Number) {
            double val1 = ((Number) op1).doubleValue();
            double val2 = ((Number) op2).doubleValue();
            return Double.compare(val1, val2);
        } else if (op1 instanceof String && op2 instanceof String) {
            return ((String) op1).compareTo((String) op2);
        } else {
            throw new InputMismatchException("Type Error: Only compare numbers with numbers and strings with strings.");
        }
    }

    /**
     * Determines if two elements are equal in value.
     * @throws EmptyStackException when the stack doesn't contain at least two elements to compare.
     */
    public void isEqual() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Object first = stack.pop();
        Object second = stack.pop();
        // check if the operands are boolean types and compare them as such
        if (second instanceof Boolean && first instanceof Boolean) {
            stack.push(second.equals(first));
            return;
        }
        stack.push(comparisonHelper(second, first) == 0);
    }

    /**
     * Determines if two elements are not equal in value.
     * @throws EmptyStackException when the stack doesn't contain at least two elements to compare.
     */
    public void notEqual() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Object first = stack.pop();
        Object second = stack.pop();
        if (second instanceof Boolean && first instanceof Boolean) {
            stack.push(!second.equals(first));
        }
        stack.push(comparisonHelper(second, first) != 0);
    }

    /**
     * Determines if one elements is greater in value than another element.
     * @throws EmptyStackException when the stack doesn't contain at least two element to compare.
     */
    public void greaterThan() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Object first = stack.pop();
        Object second = stack.pop();
        stack.push(comparisonHelper(second, first) > 0);
    }

    /**
     * Determines if one element is lesser in value than another element.
     * @throws EmptyStackException when the stack doesn't contain at least two elements to compare.
     */
    public void lessThan() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Object first = stack.pop();
        Object second = stack.pop();
        stack.push(comparisonHelper(second, first) < 0);
    }

    /**
     * Determines if one element is greater than or equal to another element.
     * @throws EmptyStackException when the stack doesn't contain at least two elements to compare.
     */
    public void greaterThanOrEqualTo() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Object first = stack.pop();
        Object second = stack.pop();
        stack.push(comparisonHelper(second, first) >= 0);
    }

    /**
     * Determines if one element is less than or equal to another element.
     * @throws EmptyStackException when the stack doesn't contain at least two elements to compare.
     */
    public void lessThanOrEqualTo() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Object first = stack.pop();
        Object second = stack.pop();
        stack.push(comparisonHelper(second, first) <= 0);
    }

    /**
     * Ties two conditions together as a logical requirement to be met in a conditional evaluation.
     * @throws EmptyStackException when the stack doesn't contain at least two elements to compare.
     * @throws InputMismatchException when the elements to compare aren't either both boolean types or both numeric types.
     */
    public void and() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Object first = stack.pop();
        Object second = stack.pop();
        // first check if the two elements to compare boolean types and compare them as such
        if (second instanceof Boolean && first instanceof Boolean) {
            stack.push((Boolean)second && (Boolean)first);
        // if they aren't boolean, check if they're numeric and compare them as such
        } else if (second instanceof Number && first instanceof Number) {
            stack.push(((Number)second).intValue() & ((Number)first).intValue());
        // and if they are neither, throw an error
        } else {
            throw new InputMismatchException("Type Error: Only tie booleans with booleans and numbers with numbers.");
        }
    }

    /**
     * Separates two conditions as individual logical requirements in a conditional evaluation,
     * where only one of them must be satisfied.
     * @throws EmptyStackException when the stack doesn't contain at least two elements to compare.
     * @throws InputMismatchException when the elements to compare aren't either both boolean types or both numeric types.
     */
    public void or() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Object first = stack.pop();
        Object second = stack.pop();
        if (second instanceof Boolean && first instanceof Boolean) {
            stack.push((Boolean)second || (Boolean)first);
        } else if (second instanceof Number && first instanceof Number) {
            stack.push(((Number)second).intValue() | ((Number)first).intValue());
        } else {
            throw new InputMismatchException("Type Error: Only compare booleans with booleans and numbers with numbers.");
        }
    }

    /**
     * Evaluates and pushes the negation of a specified operand.
     * @throws EmptyStackException when the stack doesn't contain any elements.
     * @throws InputMismatchException when a given operand isn't a boolean or numeric type.
     */
    public void not() {
        if (stack.count() < 1) {
            throw new EmptyStackException();
        }
        Object operand = stack.pop();
        if (operand instanceof Boolean) {
            stack.push(!(Boolean)operand);
        } else if (operand instanceof Number) {
            stack.push(~((Number)operand).intValue());
        } else {
            throw new InputMismatchException("Type Error: Only negate operands of numeric or boolean type.");
        }
    }

    /**
     * Pushes a boolean true onto the operand stack.
     */
    public void psTrue() {
        stack.push(true);
    }

    /**
     * Pushes a boolean false onto the operand stack.
     */
    public void psFalse() {
        stack.push(false);
    }
}