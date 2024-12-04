import java.util.*;

/**
 * Operand stack to store values returned by user entries and arithmetic operations.
 * @author Aaron Howe
 * @version Java 11
 */
public class MyStack {

    private Stack<Object> stack;

    /**
     * Default constructor.
     */
    public MyStack() {
        stack = new Stack<>();
    }

    /**
     * Adds a defined operand to the top of the operand stack.
     * @param operand Element to be added.
     */
    public void push(Object operand) {
        // if operand is a double, push right away
        if (operand instanceof Double) {
            stack.push(operand);
        // check if operand is a number
        } else if (operand instanceof Number) {
            // holds the number as its numerical (type flexible) value
            double num = ((Number) operand).doubleValue();
            // check if its a whole number, and if it is, push it as an integer
            if (num == Math.floor(num) && !Double.isInfinite(num)) {
                stack.push(((Number) operand).intValue());
            // otherwise push the double
            } else {
                stack.push(num);
            }
        } else {
            stack.push(operand);
        }
    }

    /**
     * Exchanges the element at the top of the stack with the element below it.
     */
    public void exch() {

        if (stack.size() < 2) {
            throw new EmptyStackException();
        }

        // pop the first two elements from the stack and then return them in reverse order.
        Object first = pop();
        Object second = pop();
        push(first);
        push(second);
    }

    /**
     * Removes the element at the top of the stack.
     * @return Element to be removed.
     */
    public Object pop() {

        if (stack.size() < 1) {
            throw new EmptyStackException();
        }
        return stack.pop();
    }

    /**
     * Fetches the data of but does not remove the element at the top of the stack.
     * @return Element to be 'examined'
     */
    public Object peek() {
        if (stack.size() < 1) {
            throw new EmptyStackException();
        }
        return stack.peek();
    }

    /**
     * Copies the top nth elements of the stack.
     */
    public void copy() {

        if (stack.size() < 1) {
            throw new EmptyStackException();
        }

        // element at the top of the operand stack
        Object first = pop();
        if (!(first instanceof Integer)) {
            throw new InputMismatchException("This operation can only be performed on integer values.");
        }
        // value storage for the value of the first element
        int n = (Integer) first;
        if (stack.size() < n) {
            throw new EmptyStackException();
        }
        // array of objects to store each element popped from the stack
        Object[] elements = new Object[n];

        for (int i = n - 1; i >= 0; i--) {
            elements[i] = pop();
        }
        // push each element back onto the operand stack
        for (Object element : elements) {
            push(element);
        }
        // and then push each element again to populate the stack with copies
        for (Object element : elements) {
            push(element);
        }
    }

    /**
     * Copies the element at the top of the stack and pushes the copy.
     */
    public void dup() {

        if (stack.size() < 1) {
            throw new EmptyStackException();
        }
        // store the element at the top of the stack
        Object first = pop();
        // create a copy of the popped element
        Object dup = first;
        // push both the originally popped element and its copy onto the stack.
        push(first);
        push(dup);
    }

    /**
     * Removes all elements from the stack.
     */
    public void clear() {
        stack.clear();
    }

    /**
     * Counts the number of elements on the stack and pushes this value as a new element.
     * @return Value associated with the number of elements on the operand stack.
     */
    public int count() {
        return stack.size();
    }
}