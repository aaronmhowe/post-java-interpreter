import java.util.*;

/**
 * Implements conditional logic for operations between values.
 * @author Aaron Howe
 * @version Java 11
 */
public class MyFlowControl {

    private final MyStack stack;
    private boolean terminate = false;

    /**
     * Constructor.
     */
    public MyFlowControl(MyStack stack) {
        this.stack = stack;
    }

    /**
     * Removes two specified values from the operand stack and checks whether a
     * conditional argument between them is true or false, and executes a given command
     * when true.
     * @throws EmptyStackException when there aren't at least two elements in the stack.
     */
    public void ifCondition() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Runnable procedure = executeProcedure();
        boolean args = executeBool();
        if (args) {
            procedure.run();
        }
    }

    /**
     * Alternative if-condition to check whether a different conditional argument between two
     * values is true or false when the previous if-condition fails to pass. If returns true,
     * executes a given command.
     * @throws EmptyStackException when there aren't at least three elements in the stack.
     */
    public void ifElseCondition() {
        if (stack.count() < 3) {
            throw new EmptyStackException();
        }
        Runnable proc1 = executeProcedure();
        Runnable proc2 = executeProcedure();
        boolean args = executeBool();
        if (args) {
            proc2.run();
        } else {
            proc1.run();
        }
    }

    /**
     * Executes a command over an incremental loop based on a set of conditions.
     * @throws EmptyStackException when there aren't at least four elements in the stack.
     */
    public void forLoop() {
        if (stack.count() < 4) {
            throw new EmptyStackException();
        }
        Runnable procedure = executeProcedure();
        // i = someEndPoint
        double end = executeNum();
        // i++
        double iterator = executeNum();
        // i = someStartingPoint
        double start = executeNum();
        for (double i = start; iterator >= 0 ? i <= end : i >= end; i+= iterator) {
            stack.push((int)i);
            procedure.run();
            if (terminate) {
                break;
            }
        }
    }

    /**
     * Executes a command a specified number of times.
     * @throws EmptyStackException when there aren't at least two elements in the stack.
     */
    public void repeat() {
        if (stack.count() < 2) {
            throw new EmptyStackException();
        }
        Runnable procedure = executeProcedure();
        int end = executeInt();
        for (int i = 0; i < end && !terminate; i++) {
            procedure.run();
        }
    }

    /**
     * Quits the post-script interpreter.
     */
    public void quit() {
        terminate = true;
    }

    /**
     * Quit command helper function.
     * @return Boolean class member terminate.
     */
    public boolean terminated() {
        return terminate;
    }

    /**
     * Retrieves a given procedural command from the operand stack for execution.
     * @return Procedural command.
     * @throws InputMismatchException when the provided argument is not a procedural command.
     */
    private Runnable executeProcedure() {
        Object commands = stack.pop();
        if (commands instanceof Runnable) {
            return (Runnable) commands;
        } else {
            throw new InputMismatchException("Argument should be a procedural command.");
        }
    }

    /**
     * Retrieves a boolean command from the operand stack for execution.
     * @return Boolean argument.
     * @throws InputMismatchException when the provided argument is not a boolean type.
     */
    private boolean executeBool() {
        Object args = stack.pop();
        if (args instanceof Boolean) {
            return (Boolean) args;
        } else {
            throw new InputMismatchException("Argument should be a boolean type.");
        }
    }

    /**
     * Retrieves a numeric-based operand from the operand stack for execution.
     * @return Numeric argument.
     * @throws InputMismatchException when the provided argument is not a numeric type.
     */
    private double executeNum() {
        Object args = stack.pop();
        if (args instanceof Number) {
            return ((Number)args).doubleValue();
        } else {
            throw new InputMismatchException("Argument should be a numerical type.");
        }
    }

    /**
     * Retrieves an int-based operand from the operand stack for execution.
     * @return Integer argument.
     * @throws InputMismatchException when the provided argument is not an integer type.
     */
    private int executeInt() {
        Object args = stack.pop();
        if (args instanceof Number) {
            return ((Number)args).intValue();
        } else {
            throw new InputMismatchException("Argument should be an integer type.");
        }
    }
}